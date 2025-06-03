package dtnpaletteofpaws.common.spawn;

import java.util.List;
import java.util.stream.Collectors;

import dtnpaletteofpaws.ChopinLogger;
import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.DTNPConfig;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.WolfSpawnUtil;
import dtnpaletteofpaws.common.util.WolfVariantUtil;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public class DTNWolfSpawnPlacements {

    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
            DTNEntityTypes.DTNWOLF.get(), SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            DTNWolfSpawnPlacements::DTNWolfSpawnableOnDefault,
            RegisterSpawnPlacementsEvent.Operation.OR
        );
    }

    public static boolean DTNWolfSpawnableOnDefault(EntityType<DTNWolf> type, ServerLevelAccessor level, MobSpawnType spawn_type, BlockPos pos, RandomSource random) {
        var state_below = level.getBlockState(pos.below());
        return DTNWolf.checkWolfSpawnableBlockDefault(level, pos, state_below)
            && DTNWolf.checkWolfSpawnableLight(level, pos);
    }

    public static boolean DTNWolfSpawnableOn(WolfBiomeConfig config, LevelAccessor level, MobSpawnType spawn_type, BlockPos pos, RandomSource random) {
        boolean block_is_spawnable =
            checkWolfSpawnableBlock(level, pos, config);
        if (!block_is_spawnable)
            return false;
        boolean light_condition =
            config.canSpawnInDark()
            || DTNWolf.checkWolfSpawnableLight(level, pos);
        if (!light_condition)
            return false;
        return true;
    }

    public static boolean checkWolfSpawnableBlock(LevelAccessor level, BlockPos pos, WolfBiomeConfig config) {
        var blocks = config.blocks();
        var state_below = level.getBlockState(pos.below());
        var block_below = state_below.getBlock();
        return blocks.contains(block_below)
            || DTNWolf.checkWolfSpawnableBlockDefault(level, pos, state_below);
    }

    public static boolean spawnPlacementTypeCheck(LevelReader world, BlockPos pos, WolfBiomeConfig config) {
        if (config.placementType() == WolfSpawnPlacementType.WATER) {
            return checkPossibleWaterSpawn(world, pos, config);
        }
        return SpawnPlacementTypes.ON_GROUND.isSpawnPositionOk(world, pos, DTNEntityTypes.DTNWOLF.get());
    }

    private static boolean checkPossibleWaterSpawn(LevelReader world, BlockPos pos, WolfBiomeConfig config) {
        var state = world.getBlockState(pos);
        if (!state.isAir())
            return false;
        var pos_below = pos.below();
        var water_spawnable_below = SpawnPlacementTypes.IN_WATER.isSpawnPositionOk(world, pos_below, DTNEntityTypes.DTNWOLF.get());
        if (!water_spawnable_below)
            return false;
        if (checkWaterSpawnRestrictBlock(config, world, pos))
            return false;

        return true;
    }

    private static boolean checkWaterSpawnRestrictBlock(WolfBiomeConfig config, LevelReader world, BlockPos pos) {
        var restricted_blocks = config.blocks();
        if (restricted_blocks.isEmpty())
            return false;
        final int check_y_initial = world.getHeight(Types.OCEAN_FLOOR, pos.getX(), pos.getZ());
        var check_pos = new BlockPos(pos.getX(), check_y_initial, pos.getZ());
        var check_state = world.getBlockState(check_pos);
        if (check_state.isPathfindable(world, check_pos, PathComputationType.WATER)) {
            check_pos = check_pos.below();
            check_state = world.getBlockState(check_pos);
        }
        if (!restricted_blocks.contains(check_state.getBlock()))
            return true;
        return false;
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
         if (below_state.isPathfindable(world, check_pos_below, PathComputationType.LAND) && below_state.getFluidState().isEmpty()) {
            return check_pos_below;
        }

        return check_pos;
    }

}
