package dtnpaletteofpaws.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import doggytalents.forge_imitate.atrrib.ForgeMod;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class FabricLivingEntityMixin {
    
    @ModifyArgs(
        method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"
        )
    )
    private void dtn__travel_modifySwimSpeed(Args args) {
        var self = (LivingEntity)(Object)this;
        if (!(self instanceof DTNWolf dog))
            return;
        if (!dog.isInWater() || dog.isInLava())
            return;
        final int FLOAT_INDX = 0, VEC3_INDEX = 1;
        float current = (Float) args.get(FLOAT_INDX);
        current *= dog.getAttributeValue(ForgeMod.SWIM_SPEED.holder());
        args.set(FLOAT_INDX, current);
    }

}
