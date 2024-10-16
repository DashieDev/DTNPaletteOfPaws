package dtnpaletteofpaws.dtn_support.variant;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import doggytalents.DoggyRegistries;
import doggytalents.common.variant.DogVariant;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraftforge.registries.DeferredRegister;

public class DTNSupportDogVariants {
    
    public static final DeferredRegister<DogVariant> DOG_VARIANT = DeferredRegister.create(DoggyRegistries.Keys.DOG_VARIANT, Constants.MOD_ID);

    public static final Supplier<DogVariant> CAPPUCCINO = register("cappuccino", 0xffae7437);
    public static final Supplier<DogVariant> ESPRESSO = register("espresso", 0xff280d03);
    public static final Supplier<DogVariant> LATTE = register("latte", 0xff9b7f66);
    public static final Supplier<DogVariant> MOCHA = register("mocha", 0xff5d2b00);
    public static final Supplier<DogVariant> WITHERED_SOUL = register("withered_soul", WitheredSoulDogVariant::new);
    public static final Supplier<DogVariant> MUSHROOM_RED = register("red_mushroom", 0xffc02624);
    public static final Supplier<DogVariant> MUSHROOM_BROWN = register("brown_mushroom", 0xff997453);
    public static final Supplier<DogVariant> BONITO_FLAKES = register("bonito_flakes", 0xffcb805e);
    public static final Supplier<DogVariant> KOMBU = registerSwimmer("kombu", 0xff36491d);
    public static final Supplier<DogVariant> SHITAKE = register("shitake", 0xff2d2624);
    public static final Supplier<DogVariant> ENOKI = register("enoki", 0xffe0c89e);
    public static final Supplier<DogVariant> VANILLA = register("vanilla", 0xfff1e8b6);
    public static final Supplier<DogVariant> STRAWBERRY = register("strawberry", 0xffed8395);
    public static final Supplier<DogVariant> CHOCOLATE = register("chocolate", 0xff533124);
    public static final Supplier<DogVariant> WALNUT = register("walnut", 0xffb88755);
    public static final Supplier<DogVariant> CORAL_BRAIN = registerSwimmer("coral_brain", 0xffce4e96);
    public static final Supplier<DogVariant> CORAL_BUBBLE = registerSwimmer("coral_bubble", 0xff911691);
    public static final Supplier<DogVariant> CORAL_FIRE = registerSwimmer("coral_fire", 0xffb32633);
    public static final Supplier<DogVariant> CORAL_HORN = registerSwimmer("coral_horn", 0xffe0da45);
    public static final Supplier<DogVariant> CORAL_TUBE = registerSwimmer("coral_tube", 0xff2546a2);
    public static final Supplier<DogVariant> ENDER = register("ender", EnderDogVariant::new);
    public static final Supplier<DogVariant> CHORUS = register("chorus", 0xff562e56);
    public static final Supplier<DogVariant> WANDERING_SOUL = register("wandering_soul", WanderingSoulDogVariant::new);
    public static final Supplier<DogVariant> SANGUINE = register("sanguine", SanguineDogVariant::new);
    public static final Supplier<DogVariant> DESICCATED = register("desiccated", 0xffccad8a);

    private static Supplier<DogVariant> registerSwimmer(String name, int guiColor) {
        return DOG_VARIANT.register(name, () -> new SwimmerDogVariant(name, guiColor));
    }

    private static Supplier<DogVariant> register(String name, Function<String, DogVariant> variant_creator) {
        var captured_variant = variant_creator.apply(name);
        return DOG_VARIANT.register(name, () -> captured_variant);
    }

    private static Supplier<DogVariant> register(String name, int gui_color) {
        return register(name, p -> {}, gui_color);
    }

    private static Supplier<DogVariant> register(String name, Consumer<DogVariant.Props> props_consumer, int gui_color) {
        var props = DogVariant.props(Util.getResource(name))
            .customTexture(Util.modifyPath(Util.getResource(name), x -> "textures/entity/dtnwolf/variants/wolf_" + x))
            .customTranslation(Constants.MOD_ID + ".variant." + name)
            .guiColor(gui_color);
        props_consumer.accept(props);
        final var captured_variant = new DogVariant(props);
        return DOG_VARIANT.register(name, () -> captured_variant);
    }

}
