package dtnpaletteofpaws.common.spawn;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

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
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
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
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class DTNWolfSpawnPlacements {

    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
            DTNEntityTypes.DTNWOLF.get(), SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            DTNWolfSpawnPlacements::DTNWolfSpawnableOnDefault,
            RegisterSpawnPlacementsEvent.Operation.OR
        );
    }

    public static boolean DTNWolfSpawnableOnDefault(EntityType<DTNWolf> type, ServerLevelAccessor level, EntitySpawnReason spawn_type, BlockPos pos, RandomSource random) {
        var state_below = level.getBlockState(pos.below());
        return DTNWolf.checkWolfSpawnableBlockDefault(level, pos, state_below)
            && DTNWolf.checkWolfSpawnableLight(level, pos);
    }

    public static boolean DTNWolfSpawnableOn(WolfBiomeConfig config, LevelAccessor level, EntitySpawnReason spawn_type, BlockPos pos, RandomSource random) {
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
        var placement = config.placementType() == WolfSpawnPlacementType.WATER ?
            SpawnPlacementTypes.IN_WATER : SpawnPlacementTypes.ON_GROUND;
        
        boolean spawn_in_lava = configHasLavaSpawn(config)
            && SpawnPlacementTypes.IN_LAVA
                .isSpawnPositionOk(world, pos.below(), DTNEntityTypes.DTNWOLF.get());
        if (spawn_in_lava)
            return true;
        return placement.isSpawnPositionOk(world, pos, DTNEntityTypes.DTNWOLF.get());
    }

    private static boolean configHasLavaSpawn(WolfBiomeConfig config) {
        return config.blocks().contains(Blocks.LAVA);
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
    public static BlockPos getDTNWolfTopNonCollidingPos(WolfSpawnPlacementType placementType, LevelReader world, int x, int z) {
        var height_map_type = placementType == WolfSpawnPlacementType.WATER ?
            Heightmap.Types.OCEAN_FLOOR : Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
        int inital_height = world.getHeight(height_map_type, x, z);
        var check_pos = new BlockPos(x, inital_height, z);
        if (world.dimensionType().hasCeiling()) {
            do {
                check_pos = check_pos.below();
            } while(!world.getBlockState(check_pos).isAir());
    
            var check_pos_below = check_pos.below();
            while (check_pos_below.getY() > world.getMinY() 
                && world.getBlockState(check_pos_below).isAir()) {
                check_pos = check_pos_below;
                check_pos_below = check_pos.below();
            }
        }

        var check_pos_below = check_pos.below();
        var below_state = world.getBlockState(check_pos_below);
        boolean below_state_pathfindable = below_state.isPathfindable(
            placementType == WolfSpawnPlacementType.WATER ? 
                PathComputationType.WATER : PathComputationType.LAND
        );
         if (below_state_pathfindable && below_state.getFluidState().isEmpty()) {
            return check_pos_below;
        }

        return check_pos;
    }

    public static BlockPos getRandomUndergroundPos(RandomSource random, LevelReader world, 
        int x, int z, Holder<Biome> biome) {
        
        final int max_spawn_y = -12;
        final int min_spawn_y = (biome.is(BiomeTags.IS_MOUNTAIN)) ? -53 : -47;
        boolean bound_check =  
            world.getMinY() < min_spawn_y && max_spawn_y < world.getMaxY();
        if (!bound_check)
            return new BlockPos(x, world.getMinY(), z);

        int start_at = random.nextIntBetweenInclusive(min_spawn_y, max_spawn_y);

        var check_pos = new BlockPos(x, start_at, z);
        var check_state = world.getBlockState(check_pos);
        while (!check_state.isAir() && check_pos.getY() >= min_spawn_y) {
            check_pos = check_pos.below();
            check_state = world.getBlockState(check_pos);
        }

        while (check_state.isAir() && check_pos.getY() >= min_spawn_y) {
            check_pos = check_pos.below();
            if (check_pos.getY() < min_spawn_y)
                break;
            check_state = world.getBlockState(check_pos);
        }

        check_pos = check_pos.above();

        if (check_pos.getY() <= min_spawn_y)
            return check_pos;
        
        var check_pos_below = check_pos.below();
        var below_state = world.getBlockState(check_pos_below);
        boolean below_state_pathfindable = below_state.isPathfindable(PathComputationType.LAND);
         if (below_state_pathfindable && below_state.getFluidState().isEmpty()) {
            return check_pos_below;
        }
        
        return check_pos;
    }

}
