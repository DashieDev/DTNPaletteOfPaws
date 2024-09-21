package dtnpaletteofpaws;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import doggytalents.forge_imitate.registry.DeferredRegister;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.lib.Resources;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.CherryWolfVariant;
import dtnpaletteofpaws.common.variant.CrimsonWolfVariant;
import dtnpaletteofpaws.common.variant.EnderWolfVariant;
import dtnpaletteofpaws.common.variant.MoltenWolfVariant;
import dtnpaletteofpaws.common.variant.WarpedWolfVariant;
import dtnpaletteofpaws.common.variant.WitheredSoulWolfVariant;
import dtnpaletteofpaws.common.variant.WolfVariant;

public class WolfVariants {
    
    public static final DeferredRegister<WolfVariant> DTN_WOLF_VARIANT = DeferredRegister.create(() -> DTNRegistries.DTN_WOLF_VARIANT.get(), Constants.MOD_ID);
    
    public static final Supplier<WolfVariant> CHERRY = register("cherry", CherryWolfVariant::new);
    public static final Supplier<WolfVariant> LEMONY_LIME = register("lemony_lime");
    public static final Supplier<WolfVariant> HIMALAYAN_SALT = register("himalayan_salt");
    public static final Supplier<WolfVariant> BAMBOO = register("bamboo");
    public static final Supplier<WolfVariant> CRIMSON = register("crimson", CrimsonWolfVariant::new);
    public static final Supplier<WolfVariant> BIRCH = register("birch");
    public static final Supplier<WolfVariant> WARPED = register("warped", WarpedWolfVariant::new);
    public static final Supplier<WolfVariant> PISTACHIO = register("pistachio");
    public static final Supplier<WolfVariant> GUACAMOLE = register("guacamole");
    public static final Supplier<WolfVariant> MOLTEN = register("molten", MoltenWolfVariant::new);
    public static final Supplier<WolfVariant> YUZU = register("yuzu");
    public static final Supplier<WolfVariant> CAPPUCCINO = register("cappuccino");
    public static final Supplier<WolfVariant> ESPRESSO = register("espresso");
    public static final Supplier<WolfVariant> LATTE = register("latte");
    public static final Supplier<WolfVariant> MOCHA = register("mocha");
    public static final Supplier<WolfVariant> WITHERED_SOUL = register("withered_soul", WitheredSoulWolfVariant::new);
    public static final Supplier<WolfVariant> MUSHROOM_RED = register("red_mushroom");
    public static final Supplier<WolfVariant> MUSHROOM_BROWN = register("brown_mushroom");
    public static final Supplier<WolfVariant> BONITO_FLAKES = register("bonito_flakes");
    public static final Supplier<WolfVariant> KOMBU = register("kombu", p -> { p.swimUnderwater(); });
    public static final Supplier<WolfVariant> SHITAKE = register("shitake");
    public static final Supplier<WolfVariant> ENOKI = register("enoki");
    public static final Supplier<WolfVariant> VANILLA = register("vanilla");
    public static final Supplier<WolfVariant> STRAWBERRY = register("strawberry");
    public static final Supplier<WolfVariant> CHOCOLATE = register("chocolate");
    public static final Supplier<WolfVariant> WALNUT = register("walnut");
    public static final Supplier<WolfVariant> CORAL_BRAIN = register("coral_brain", p -> { p.swimUnderwater(); });
    public static final Supplier<WolfVariant> CORAL_BUBBLE = register("coral_bubble", p -> { p.swimUnderwater(); });
    public static final Supplier<WolfVariant> CORAL_FIRE = register("coral_fire", p -> { p.swimUnderwater(); });
    public static final Supplier<WolfVariant> CORAL_HORN = register("coral_horn", p -> { p.swimUnderwater(); });
    public static final Supplier<WolfVariant> CORAL_TUBE = register("coral_tube", p -> { p.swimUnderwater(); });
    public static final Supplier<WolfVariant> ENDER = register("ender", EnderWolfVariant::new);
    public static final Supplier<WolfVariant> CHORUS = register("chorus");

    private static Supplier<WolfVariant> register(String name, Function<String, WolfVariant> variant_creator) {
        var captured_variant = variant_creator.apply(name);
        return DTN_WOLF_VARIANT.register(name, () -> captured_variant);
    }

    private static Supplier<WolfVariant> register(String name) {
        return register(name, p -> {});
    }

    private static Supplier<WolfVariant> register(String name, Consumer<WolfVariant.Props> props_consumer) {
        var props = WolfVariant.props(name);
        props_consumer.accept(props);
        final var captured_variant = new WolfVariant(props);
        return DTN_WOLF_VARIANT.register(name, () -> captured_variant);
    }

}
