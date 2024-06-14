package dtnpaletteofpaws.common.spawn;

import dtnpaletteofpaws.ChopinLogger;
import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.WolfSpawnUtil;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public class DTNWolfSpawnPlacements {
    
    public static final SpawnPlacements.Type DTN_WOLF_SPAWN_TYPE
        = SpawnPlacements.Type.create(Constants.MOD_ID + "_DTN_WOLF_SPAWN_TYPE", 
        DTNWolfSpawnPlacements::spawnPlacementTypeCheck);

    public static void onRegisterSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
            DTNEntityTypes.DTNWOLF.get(), DTN_WOLF_SPAWN_TYPE,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            DTNWolfSpawnPlacements::DTNWolfSpawnableOn,
            SpawnPlacementRegisterEvent.Operation.OR
        );
    }

    public static boolean DTNWolfSpawnableOn(EntityType<DTNWolf> type, LevelAccessor level, MobSpawnType spawn_type, BlockPos pos, RandomSource random) {
        if (checkBasedOnExtraSpawnableBlocksForBiomes(level, spawn_type, pos))
            return true;

        return DTNWolf.checkWolfSpawnRulesDefault(type, level, spawn_type, pos, random);
    }

    public static boolean checkBasedOnExtraSpawnableBlocksForBiomes(LevelAccessor level, MobSpawnType spawn_type, BlockPos pos) {
        var state_under = level.getBlockState(pos.below());
        var block_under = state_under.getBlock();
        var biome = level.getBiome(pos);
        var extra_block_set = WolfSpawnUtil.getExtraSpawnableBlocksForBiome(level.registryAccess(), biome);
        if (extra_block_set.contains(block_under))
            return true;
        return false;
    }

    public static boolean spawnPlacementTypeCheck(LevelReader world, BlockPos pos, EntityType<?> type) {
        return NaturalSpawner.canSpawnAtBody(SpawnPlacements.Type.ON_GROUND, world, pos, type);
    }

    /**
     * To workaround the inconsistency of NaturalSpawnwer::getTopNonCollidingPos.
     * The function returns the pos below the TopNonCollingPos for biome which hasCeling instead.
     * Which causes all of the further ON_GROUND spawn checks to fail despite valid spawn pos.
     * This function simply shift the pos one up if biome hasCeiling so further checks will based on
     * the actual TopNonCollidingPos.
     */
    public static BlockPos getNetherSpawnTopNonCollidingPos(EntityType<DTNWolf> type, LevelReader world, int x, int z) {
        int inital_height = world.getHeight(SpawnPlacements.getHeightmapType(type), x, z);
        var check_pos = new BlockPos(x, inital_height, z);
        if (!world.dimensionType().hasCeiling())
            return check_pos;
 
        ChopinLogger.l("getting fixed TopNonCollidingPos for pos : " + x + " " + z);

        do {
            check_pos = check_pos.below();
        } while(!world.getBlockState(check_pos).isAir());

        var check_pos_below = check_pos.below();
        while (check_pos_below.getY() > world.getMinBuildHeight() 
            && world.getBlockState(check_pos_below).isAir()) {
            check_pos = check_pos_below;
            check_pos_below = check_pos.below();
        }

        return check_pos.immutable();
    }

}
