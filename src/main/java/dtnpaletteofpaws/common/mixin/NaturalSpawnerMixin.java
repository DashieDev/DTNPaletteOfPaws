package dtnpaletteofpaws.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnPlacements;
import dtnpaletteofpaws.common.spawn.DTNWolfStaticSpawnManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    
    // @Inject(method = "getTopNonCollidingPos", at = @At(value = "HEAD"), cancellable = true)
    // private static void dtn_getTopNonCollidingPos(LevelReader level, EntityType<?> type, int x, int z, CallbackInfoReturnable<BlockPos> info) {
    //     if (type != DTNEntityTypes.DTNWOLF.get())
    //         return;
        
    //     var pos = DTNWolfSpawnPlacements.getDTNWolfTopNonCollidingPos(
    //         DTNEntityTypes.DTNWOLF.get(), level, x, z);
        
    //     info.setReturnValue(pos);
    // }

    @Inject(method = "spawnMobsForChunkGeneration", at = @At(value = "HEAD"), cancellable = false)
    private static void dtn_spawnMobsForChunkGeneration(ServerLevelAccessor level_accessor, Holder<Biome> biome, ChunkPos chunk_pos, RandomSource random, CallbackInfo info) {
        DTNWolfStaticSpawnManager.get().onChunkGenerationMobSpawn(level_accessor, biome, chunk_pos, random);
    }

}
