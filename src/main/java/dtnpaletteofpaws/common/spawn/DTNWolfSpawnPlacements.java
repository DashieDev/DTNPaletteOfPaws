package dtnpaletteofpaws.common.spawn;

import java.util.List;
import java.util.stream.Collectors;

import dtnpaletteofpaws.ChopinLogger;
import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.WolfSpawnUtil;
import dtnpaletteofpaws.common.util.WolfVariantUtil;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathComputationType;
public class DTNWolfSpawnPlacements {
    
    // public static final SpawnPlacements.Type DTN_WOLF_SPAWN_TYPE
    //     = SpawnPlacements.Type.create(Constants.MOD_ID + "_DTN_WOLF_SPAWN_TYPE", 
    //     DTNWolfSpawnPlacements::spawnPlacementTypeCheck);

    public static void init() {
        SpawnPlacements.register(DTNEntityTypes.DTNWOLF.get(), 
            SpawnPlacements.Type.ON_GROUND, 
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 
            DTNWolfSpawnPlacements::DTNWolfSpawnableOn);   
    }

    public static boolean DTNWolfSpawnableOn(EntityType<DTNWolf> type, LevelAccessor level, MobSpawnType spawn_type, BlockPos pos, RandomSource random) {
        var biome = level.getBiome(pos);
        var configs = WolfVariantUtil.getAllWolfBiomeConfigForBiome(level.registryAccess(), biome);
        boolean block_is_spawnable =
            checkWolfSpawnableBlock(level, pos, configs);
        if (!block_is_spawnable)
            return false;
        boolean light_condition =
            WolfVariantUtil.checkCanSpawnInTheDarkForConfigs(configs)
            || DTNWolf.checkWolfSpawnableLight(level, pos);
        if (!light_condition)
            return false;
        return true;
    }

    public static boolean checkWolfSpawnableBlock(LevelAccessor level, BlockPos pos, List<WolfBiomeConfig> configs) {
        if (checkBasedOnExtraSpawnableBlocksForBiomes(level, pos, configs))
            return true;
        return DTNWolf.checkWolfSpawnableBlockDefault(level, pos);
    }

    public static boolean checkBasedOnExtraSpawnableBlocksForBiomes(LevelAccessor level, BlockPos pos, List<WolfBiomeConfig> configs) {
        var state_below = level.getBlockState(pos.below());
        var extra_block_set = WolfVariantUtil.getExtraSpawnableBlocksForBiomeConfigs(configs);
        if (extra_block_set.contains(state_below.getBlock()))
            return true;
        return false;
    }

    public static boolean spawnPlacementTypeCheck(LevelReader world, BlockPos pos, EntityType<?> type) {
        if (SpawnPlacementTypes.ON_GROUND.isSpawnPositionOk(world, pos, type))
            return true;
        if (checkPossibleWaterSpawn(world, pos, type))
            return true;
            
        return false;
    }

    private static boolean checkPossibleWaterSpawn(LevelReader world, BlockPos pos, EntityType<?> type) {
        var state = world.getBlockState(pos);
        if (!state.isAir())
            return false;
        var pos_below = pos.below();
        var water_spawnable_below = SpawnPlacementTypes.IN_WATER.isSpawnPositionOk(world, pos_below, type);
        if (!water_spawnable_below)
            return false;
        var biome = world.getBiome(pos);
        var configs = WolfVariantUtil.getAllWolfBiomeConfigForBiome(world.registryAccess(), biome)
            .stream().filter(x -> x.blocks().contains(Blocks.WATER))
            .collect(Collectors.toList());
        if (configs.isEmpty())
            return false;
        return true;
    }


    /**
     * Seperated getTopNonCollidingPos for DTNWolf for two reason
     * 
     * + To replicate the hardcoded ON_GROUND additional below pathfindable check.
     * 
     * + To workaround the inconsistency of NaturalSpawnwer::getTopNonCollidingPos.
     * The function returns the pos below the TopNonCollingPos for biome which hasCeling instead.
     * Which causes all of the further ON_GROUND spawn checks to fail despite valid spawn pos.
     * This function override the check solely for getting the pos for DTNWolf and 
     * return the actual TopNonCollidingPos for hasCeiling dimensions.
     */
    public static BlockPos getDTNWolfTopNonCollidingPos(EntityType<DTNWolf> type, LevelReader world, int x, int z) {
        int inital_height = world.getHeight(SpawnPlacements.getHeightmapType(type), x, z);
        var check_pos = new BlockPos(x, inital_height, z);
        if (world.dimensionType().hasCeiling()) {
            do {
                check_pos = check_pos.below();
            } while(!world.getBlockState(check_pos).isAir());
    
            var check_pos_below = check_pos.below();
            while (check_pos_below.getY() > world.getMinBuildHeight() 
                && world.getBlockState(check_pos_below).isAir()) {
                check_pos = check_pos_below;
                check_pos_below = check_pos.below();
            }
        }

        var check_pos_below = check_pos.below();
        var below_state = world.getBlockState(check_pos_below);
         if (below_state.isPathfindable(PathComputationType.LAND) && below_state.getFluidState().isEmpty()) {
            return check_pos_below;
        }

        return check_pos;
    }

}
