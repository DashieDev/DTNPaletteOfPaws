package dtnpaletteofpaws.dtn_support.variant;

import java.util.function.Consumer;
import java.util.function.Supplier;

import doggytalents.DoggyRegistries;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraftforge.registries.DeferredRegister;

public class DTNSupportDogVariants {
    
    public static final DeferredRegister<DogVariant> DOG_VARIANT = DeferredRegister.create(DoggyRegistries.Keys.DOG_VARIANT, Constants.MOD_ID);

    public static final Supplier<DogVariant> CAPPUCCINO = register("cappuccino");
    public static final Supplier<DogVariant> ESPRESSO = register("espresso");
    public static final Supplier<DogVariant> LATTE = register("latte");
    public static final Supplier<DogVariant> MOCHA = register("mocha");

    private static Supplier<DogVariant> register(String name) {
        return register(name, p -> {});
    }

    private static Supplier<DogVariant> register(String name, Consumer<DogVariant.Props> props_consumer) {
        var props = DogVariant.props(Util.getResource(name))
            .customTexture(Util.modifyPath(Util.getResource(name), x -> "textures/entity/dtnwolf/variants/wolf_" + x));
        props_consumer.accept(props);
        final var captured_variant = new DogVariant(props);
        return DOG_VARIANT.register(name, () -> captured_variant);
    }

}
