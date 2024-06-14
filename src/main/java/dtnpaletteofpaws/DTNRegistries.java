package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class DTNRegistries {

    public static class Keys {
        public static final ResourceLocation DTN_WOLF_VARIANT = Util.getResource("dtn_wolf_variant"); 
    }

    public static class DataKeys {
        public static final ResourceKey<Registry<WolfBiomeConfig>> WOLF_BIOME_CONFIG = Util.getRegistryKey(WolfBiomeConfig.class, "wolf_biome_config");
    }

    public static Supplier<IForgeRegistry<WolfVariant>> DTN_WOLF_VARIANT;
    
    public static void onNewRegistry(NewRegistryEvent event) {
        DTN_WOLF_VARIANT = event.create(makeRegistry(Keys.DTN_WOLF_VARIANT, WolfVariant.class));
    }

    private static <T> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
        return new RegistryBuilder<T>().setName(rl);
    }

    public static void onNewDataRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(DataKeys.WOLF_BIOME_CONFIG, WolfBiomeConfig.CODEC);
    }

}
