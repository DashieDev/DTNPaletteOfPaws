package dtnpaletteofpaws;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.lib.Resources;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.MoltenWolfVariant;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraftforge.registries.DeferredRegister;

public class WolfVariants {
    
    public static final DeferredRegister<WolfVariant> DTN_WOLF_VARIANT = DeferredRegister.create(DTNRegistries.Keys.DTN_WOLF_VARIANT, Constants.MOD_ID);
    
    public static final Supplier<WolfVariant> CHERRY = register("cherry");
    public static final Supplier<WolfVariant> LEMONY_LIME = register("lemony_lime");
    public static final Supplier<WolfVariant> HIMALAYAN_SALT = register("himalayan_salt");
    public static final Supplier<WolfVariant> BAMBOO = register("bamboo");
    public static final Supplier<WolfVariant> CRIMSON = register("crimson");
    public static final Supplier<WolfVariant> BIRCH = register("birch");
    public static final Supplier<WolfVariant> WARPED = register("warped");
    public static final Supplier<WolfVariant> PISTACHIO = register("pistachio");
    public static final Supplier<WolfVariant> GUACAMOLE = register("guacamole");
    public static final Supplier<WolfVariant> MOLTEN = register("molten", MoltenWolfVariant::new);

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
