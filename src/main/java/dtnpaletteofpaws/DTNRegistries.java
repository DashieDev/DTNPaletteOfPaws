package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DTNRegistries {

    public static class Keys {
        public static final ResourceLocation DTN_WOLF_VARIANT = Util.getResource("dtn_wolf_variant"); 
    }

    public static class DataKeys {
        public static final ResourceKey<Registry<WolfBiomeConfig>> WOLF_BIOME_CONFIG = Util.getRegistryKey(WolfBiomeConfig.class, "wolf_biome_config");
    }

    public static Supplier<Registry<WolfVariant>> DTN_WOLF_VARIANT;
    
    public static void newRegistry() {
        DTN_WOLF_VARIANT = makeRegistry(Keys.DTN_WOLF_VARIANT, WolfVariant.class, Util.getResource("birch"));
    }

    private static <T> Supplier<Registry<T>> makeRegistry(
        final ResourceLocation key, Class<T> type, ResourceLocation defaultKey) {
        var ret =  FabricRegistryBuilder.createDefaulted(ResourceKey.<T>createRegistryKey(key), defaultKey)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
        return () -> ret;
    }

    public static void newDataRegistry() {
        DynamicRegistries.register(DataKeys.WOLF_BIOME_CONFIG, WolfBiomeConfig.CODEC);
    }
}
