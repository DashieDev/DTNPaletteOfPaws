package dtnpaletteofpaws.dtn_support.variant;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.DTNParticles;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.lib.Resources;
import dtnpaletteofpaws.common.util.RandomUtil;
import dtnpaletteofpaws.common.util.Util;

public class WanderingSoulDogVariant extends DogVariant implements IDogAlteration {

    public WanderingSoulDogVariant(String name) {
        super(
            DogVariant.props(Util.getResource(name))
            .customTexture(Util.modifyPath(Util.getResource(name), x -> "textures/entity/dtnwolf/variants/wolf_" + x))
            .glowingOverlay(
                Resources.getDTNWolfTexture("variants/wolf_wandering_soul")
            )
            .customTranslation(Constants.MOD_ID + ".variant." + name)
            .guiColor(0xff88f6f6)
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
        double dx = dog.getX() - dog.xo;
        double dz = dog.getZ() - dog.zo;
        //var is_moving = dx * dx + dz * dz > (double)2.5000003E-7F;
        if (dog.getRandom().nextInt(3) == 0) {
            double d0 = (double)dog.getX() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
            double d1 = (double)dog.getY() + random.nextFloat() * (dog.getBbHeight() * 0.8);
            double d2 = (double)dog.getZ() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
            level.addParticle(DTNParticles.WANDERING_SOUL_GLOW.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
    
}
