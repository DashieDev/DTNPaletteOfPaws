package dtnpaletteofpaws.dtn_support.variant;

import java.util.function.Supplier;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.common.variant.WolfVariant;

public class DTNWrappedVariant extends DogVariant implements IDogAlteration {

    private Supplier<WolfVariant> wolf_variant;

    public DTNWrappedVariant(Props props, Supplier<WolfVariant> wolf_variant_sup) {
        super(props);
        this.wolf_variant = wolf_variant_sup;
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        var variant = wolf_variant.get();
        if (variant.fireImmune())
            props.setFireImmune();
        if (variant.fallImmune())
            props.setFallImmune();
    }

}
