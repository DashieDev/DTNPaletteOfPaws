package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class PawsRegistries {

    public static class Keys {
        public static final ResourceLocation DTN_WOLF_VARIANT = Util.getResource("dtn_wolf_variant"); 
    }

    public static Supplier<IForgeRegistry<WolfVariant>> DTN_WOLF_VARIANT;
    
    public static void onNewRegistry(NewRegistryEvent event) {
        DTN_WOLF_VARIANT = event.create(makeRegistry(Keys.DTN_WOLF_VARIANT, WolfVariant.class));
    }

    private static <T> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
        return new RegistryBuilder<T>().setName(rl);
    }

}
