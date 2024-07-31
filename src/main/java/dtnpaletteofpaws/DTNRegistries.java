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
        public static final ResourceKey<Registry<WolfVariant>> DTN_WOLF_VARIANT = regKey("dtn_wolf_variant");
        
        private static <T> ResourceKey<Registry<T>> regKey(String key) {
            var rl = Util.getResource(key);
            return ResourceKey.createRegistryKey(rl);
        }
    }

    public static class DataKeys {
        public static final ResourceKey<Registry<WolfBiomeConfig>> WOLF_BIOME_CONFIG = Util.getRegistryKey(WolfBiomeConfig.class, "wolf_biome_config");
    }

    public static Supplier<Registry<WolfVariant>> DTN_WOLF_VARIANT;
    
    public static void newRegistry() {
        DTN_WOLF_VARIANT = makeRegistry(Keys.DTN_WOLF_VARIANT, WolfVariant.class, Util.getResource("birch"));
    }

    private static <T> Supplier<Registry<T>> makeRegistry(
        final ResourceKey<Registry<T>> key, Class<T> type, ResourceLocation defaultKey) {
        var ret =  FabricRegistryBuilder.createDefaulted(key, defaultKey)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
        return () -> ret;
    }

    public static void newDataRegistry() {
        DynamicRegistries.register(DataKeys.WOLF_BIOME_CONFIG, WolfBiomeConfig.CODEC);
    }
}
