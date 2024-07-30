package dtnpaletteofpaws.common.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnPlacements;
import dtnpaletteofpaws.common.spawn.DTNWolfVariantsFabricSpawn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    
    @Inject(method = "getTopNonCollidingPos", at = @At(value = "HEAD"), cancellable = true)
    private static void dtn_getTopNonCollidingPos(LevelReader level, EntityType<?> type, int x, int z, CallbackInfoReturnable<BlockPos> info) {
        if (type != DTNEntityTypes.DTNWOLF.get())
            return;
        
        var pos = DTNWolfSpawnPlacements.getDTNWolfTopNonCollidingPos(
            DTNEntityTypes.DTNWOLF.get(), level, x, z);
        
        info.setReturnValue(pos);
    }

    @Inject(method = "isSpawnPositionOk(Lnet/minecraft/world/entity/SpawnPlacements/Type;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/EntityType;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void dtn_isSpawnPositionOk(SpawnPlacements.Type type, LevelReader levelReader, BlockPos blockPos, @Nullable EntityType<?> entityType, CallbackInfoReturnable<Boolean> info) {
        if (entityType != DTNEntityTypes.DTNWOLF.get())
            return;
        var ret = DTNWolfSpawnPlacements.spawnPlacementTypeCheck(levelReader, blockPos, entityType);
        info.setReturnValue(ret);
    }

}
