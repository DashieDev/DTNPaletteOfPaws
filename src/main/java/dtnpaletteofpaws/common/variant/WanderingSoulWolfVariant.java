package dtnpaletteofpaws.common.variant;

import dtnpaletteofpaws.DTNParticles;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Resources;
import dtnpaletteofpaws.common.util.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class WanderingSoulWolfVariant extends WolfVariant {
    
    public WanderingSoulWolfVariant(String name) {
        super(
            WolfVariant.props(name)
            .fireImmune()
            .fallImmune()
            .glowingOverlay(
                Resources.getDTNWolfTexture("variants/wolf_wandering_soul"),
                Resources.getDTNWolfTexture("variants/wolf_wandering_soul_wild")
            )
        );
    }

    @Override
    public void tickWolf(DTNWolf wolf) {
        super.tickWolf(wolf);

        if (!wolf.level().isClientSide)
            return;

        var random = wolf.getRandom();
        var level = wolf.level();
        double dx = wolf.getX() - wolf.xo;
        double dz = wolf.getZ() - wolf.zo;
        //var is_moving = dx * dx + dz * dz > (double)2.5000003E-7F;
        if (wolf.getRandom().nextInt(3) == 0) {
            double d0 = (double)wolf.getX() + RandomUtil.nextFloatRemapped(random) * (wolf.getBbWidth()/2);
            double d1 = (double)wolf.getY() + random.nextFloat() * (wolf.getBbHeight() * 0.8);
            double d2 = (double)wolf.getZ() + RandomUtil.nextFloatRemapped(random) * (wolf.getBbWidth()/2);
            level.addParticle(DTNParticles.WANDERING_SOUL_GLOW.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
    

    

}
