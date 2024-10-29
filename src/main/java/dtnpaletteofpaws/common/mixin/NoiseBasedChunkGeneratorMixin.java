package dtnpaletteofpaws.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dtnpaletteofpaws.common.spawn.DTNWolfStaticSpawnManager;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
    
    @Inject(method = "spawnOriginalMobs", at = @At(value = "HEAD"), cancellable = false)
    public void dtn_spawnOriginalMobs(WorldGenRegion region, CallbackInfo info) {
        DTNWolfStaticSpawnManager.onSpawnOriginalMobsForChunk(region);
    }

}
