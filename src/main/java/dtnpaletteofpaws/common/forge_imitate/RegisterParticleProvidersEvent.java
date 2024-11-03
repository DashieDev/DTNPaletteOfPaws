package dtnpaletteofpaws.common.forge_imitate;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class RegisterParticleProvidersEvent extends Event {
    
    private final ParticleEngine particleEngine;

    public RegisterParticleProvidersEvent(ParticleEngine particleEngine) {
        this.particleEngine = particleEngine;
    }

    public <T extends ParticleOptions> void registerSpriteSet(ParticleType<T> type, ParticleEngine.SpriteParticleRegistration<T> registration) {
        particleEngine.register(type, registration);
    }

}
