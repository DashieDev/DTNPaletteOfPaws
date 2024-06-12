package dtnpaletteofpaws;

import java.util.function.Consumer;
import java.util.function.Supplier;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraftforge.registries.DeferredRegister;

public class WolfVariants {
    
    public static final DeferredRegister<WolfVariant> DTN_WOLF_VARIANT = DeferredRegister.create(PawsRegistries.Keys.DTN_WOLF_VARIANT, Constants.MOD_ID);

    public static final Supplier<WolfVariant> MOLTEN_WOLF = register("molten", b -> {
        b.fireImmune();
    });

    private static Supplier<WolfVariant> register(String name, Consumer<WolfVariant.Builder> builder_consumer) {
        var builder = WolfVariant.builder(name);
        builder_consumer.accept(builder);
        var variant = builder.build();
        return DTN_WOLF_VARIANT.register(name, () -> variant);
    }

}
