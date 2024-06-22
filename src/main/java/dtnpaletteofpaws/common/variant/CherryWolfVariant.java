package dtnpaletteofpaws.common.variant;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class CherryWolfVariant extends WolfVariant {

    public CherryWolfVariant(String name) {
        super(WolfVariant.props(name));       
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
        var is_moving = dx * dx + dz * dz > (double)2.5000003E-7F;
        if (is_moving && wolf.getRandom().nextInt(3) == 0) {
            double d0 = (double)wolf.getX() + random.nextFloat() * (wolf.getBbWidth()/2);
            double d1 = (double)wolf.getY() + random.nextFloat() * (wolf.getBbHeight() * 0.8);
            double d2 = (double)wolf.getZ() + random.nextFloat() * (wolf.getBbWidth()/2);
            level.addParticle(ParticleTypes.CHERRY_LEAVES, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        // if (random.nextInt(200) == 0) {
        //     level.playLocalSound((double)wolf.getX(), (double)wolf.getY(), (double)wolf.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.15f, 0.9F + random.nextFloat() * 0.15F, false);
        // }
    }
    
}
