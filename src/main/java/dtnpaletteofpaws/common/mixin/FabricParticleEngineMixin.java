package dtnpaletteofpaws.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dtnpaletteofpaws.common.forge_imitate.ModEventCallbacksRegistry;
import dtnpaletteofpaws.common.forge_imitate.RegisterParticleProvidersEvent;
import net.minecraft.client.particle.ParticleEngine;

@Mixin(ParticleEngine.class)
public class FabricParticleEngineMixin {
    
    @Inject(
        method = "registerProviders()V", 
        at = @At("TAIL")
    )
    private void dtn__registerProviders(CallbackInfo info) {
        var self = (ParticleEngine)(Object)this;
        ModEventCallbacksRegistry.postEvent(new RegisterParticleProvidersEvent(self));
    }

}
