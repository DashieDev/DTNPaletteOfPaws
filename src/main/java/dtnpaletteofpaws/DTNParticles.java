package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.lib.Constants;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DTNParticles {
    
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Constants.MOD_ID);
    
    public static final Supplier<SimpleParticleType> WANDERING_SOUL_GLOW = registerSimple("wandering_soul_glow", true);

    private static Supplier<SimpleParticleType> registerSimple(String id, boolean override_limiter) {
        return PARTICLES.register(id, () -> new SimpleParticleType(override_limiter));
    }

}
