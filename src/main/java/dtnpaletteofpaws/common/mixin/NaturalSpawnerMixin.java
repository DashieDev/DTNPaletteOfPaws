package dtnpaletteofpaws.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnPlacements;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
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

}
