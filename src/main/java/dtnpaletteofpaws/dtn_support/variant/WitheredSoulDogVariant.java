package dtnpaletteofpaws.dtn_support.variant;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.lib.Resources;
import dtnpaletteofpaws.common.util.RandomUtil;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.core.particles.ParticleTypes;

public class WitheredSoulDogVariant extends DogVariant implements IDogAlteration {

    public WitheredSoulDogVariant(String name) {
        super(
            DogVariant.props(Util.getResource(name))
            .customTexture(Util.modifyPath(Util.getResource(name), x -> "textures/entity/dtnwolf/variants/wolf_" + x))
            .glowingOverlay(
                Resources.getDTNWolfTexture("variants/wolf_withered_soul_overlay")
            )
            .customTranslation(Constants.MOD_ID + ".variant." + name)
            .guiColor(0xff01a7ac)
            .burnsPetter()
            .preventWetShade()
        );
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setFireImmune();
        props.setFallImmune();
    }

    @Override
    public void tick(AbstractDog dog) {
        if (!dog.level().isClientSide)
            return;
        if (!doggytalents.common.config.ConfigHandler.CLIENT.DOG_VARIANT_CLIENT_EFFECT.get())
            return;
        if (!dog.level().isClientSide)
            return;
        
        var random = dog.getRandom();
        var level = dog.level();

        // double dx = wolf.getX() - wolf.xo;
        // double dz = wolf.getZ() - wolf.zo;
        // var is_moving = dx * dx + dz * dz > (double)2.5000003E-7F;
        if (dog.getRandom().nextInt(3) == 0) {
            var type = ParticleTypes.SOUL_FIRE_FLAME;    
            if (type != null) {
                double d0 = (double)dog.getX() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
                double d1 = (double)dog.getY() + dog.getBbHeight();
                double d2 = (double)dog.getZ() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
                level.addParticle(type, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
            
        }
    }
    
}
