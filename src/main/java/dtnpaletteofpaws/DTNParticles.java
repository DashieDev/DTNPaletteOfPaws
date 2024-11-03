package dtnpaletteofpaws;

import java.util.function.Supplier;

import doggytalents.forge_imitate.registry.DeferredRegister;
import dtnpaletteofpaws.common.lib.Constants;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class DTNParticles {
    
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(() -> BuiltInRegistries.PARTICLE_TYPE, Constants.MOD_ID);
    
    public static final Supplier<SimpleParticleType> WANDERING_SOUL_GLOW = registerSimple("wandering_soul_glow", true);

    private static Supplier<SimpleParticleType> registerSimple(String id, boolean override_limiter) {
        return PARTICLES.register(id, () -> FabricParticleTypes.simple(override_limiter));
    }

}
