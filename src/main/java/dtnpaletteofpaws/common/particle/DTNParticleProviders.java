package dtnpaletteofpaws.common.particle;

import dtnpaletteofpaws.DTNParticles;
import dtnpaletteofpaws.common.forge_imitate.RegisterParticleProvidersEvent;

public class DTNParticleProviders {
    
    public static void onRegisterProv(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(DTNParticles.WANDERING_SOUL_GLOW.get(), WanderingSoulGlowParticleProvider::new);
    }

}
