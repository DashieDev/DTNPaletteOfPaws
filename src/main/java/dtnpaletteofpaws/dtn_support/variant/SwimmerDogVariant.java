package dtnpaletteofpaws.dtn_support.variant;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;

public class SwimmerDogVariant extends DogVariant implements IDogAlteration {

    public SwimmerDogVariant(String name, int color) {
        super(
            DogVariant.props(Util.getResource(name))
            .customTexture(Util.modifyPath(Util.getResource(name), x -> "textures/entity/dtnwolf/variants/wolf_" + x))
            .customTranslation(Constants.MOD_ID + ".variant." + name)
            .guiColor(color)
        );
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setCanSwimUnderwater();
        props.setCanBreatheUnderwater();
        props.addBonusSwimSpeed(7);
    }
}