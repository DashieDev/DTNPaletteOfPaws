package dtnpaletteofpaws.common.particle;

import dtnpaletteofpaws.DTNParticles;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

public class DTNParticleProviders {
    
    public static void onRegisterProv(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(DTNParticles.WANDERING_SOUL_GLOW.get(), WanderingSoulGlowParticleProvider::new);
    }

}
