package dtnpaletteofpaws.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class WanderingSoulGlowParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public WanderingSoulGlowParticleProvider(SpriteSet p_172172_) {
        this.sprite = p_172172_;
    }

    @Override
    public Particle createParticle(SimpleParticleType particle_type,
        ClientLevel level,
        double x,
        double y,
        double z,
        double dx,
        double dy,
        double dz
    ) {
        var glowparticle = new WanderingSoulGlowParticle(
            level,
            x, y, z,
            0.5 - level.random.nextDouble(),
            dy,
            0.5 - level.random.nextDouble(),
            this.sprite
        );
        int r = level.random.nextInt(3);
        if (r == 0) {
            glowparticle.setColor(0.41f, 0.96f, 0.95f);
        } else if (r == 1) {
            glowparticle.setColor(0f, 0.95f, 1);
        } else {
            glowparticle.setColor(0.64F, 0.96F, 0.96F);
        }

        int lifetime = (int) ( 8.0 / (0.2 + level.random.nextDouble() * 0.8)); 

        glowparticle.setLifetime(lifetime);
        return glowparticle;
    }

    private static class WanderingSoulGlowParticle extends GlowParticle {

        public WanderingSoulGlowParticle(ClientLevel level, double x, double y, double z,
                double dx, double dy, double dz, SpriteSet sprite_set) {
            super(level, x, y, z, dx, dy, dz, sprite_set);
            this.yd *= 0.2f;
        }

    }
}