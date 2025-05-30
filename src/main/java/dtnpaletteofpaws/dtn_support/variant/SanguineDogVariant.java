package dtnpaletteofpaws.dtn_support.variant;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;

public class SanguineDogVariant extends DogVariant implements IDogAlteration {

    public SanguineDogVariant(String name) {
        super(
            DogVariant.props(Util.getResource(name))
                .customTexture(Util.modifyPath(Util.getResource(name), x -> "textures/entity/dtnwolf/variants/wolf_" + x))
                .customTranslation(Constants.MOD_ID + ".variant." + name)
                .guiColor(0xff490000)
        );
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setFireImmune();
    }
    
}
