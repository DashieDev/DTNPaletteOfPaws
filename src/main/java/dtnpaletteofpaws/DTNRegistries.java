package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

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

    public static final Supplier<Registry<WolfVariant>> DTN_WOLF_VARIANT = makeRegistry(Keys.DTN_WOLF_VARIANT, WolfVariant.class, Util.getResource("birch"));;
    
    public static void onNewRegistry(NewRegistryEvent event) {
        // DTN_WOLF_VARIANT = event.create(makeRegistry(event, Keys.DTN_WOLF_VARIANT, WolfVariant.class)
        //     .setDefaultKey(Util.getResource("birch")));
        event.register(DTN_WOLF_VARIANT.get());
    }

    // private static <T> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
    //     return new RegistryBuilder<T>().setName(rl);
    // }

    public static void onNewDataRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(DataKeys.WOLF_BIOME_CONFIG, WolfBiomeConfig.CODEC);
    }

    private static <T> ResourceKey<Registry<T>> regKey(String key) {
        var rl = Util.getResource(key);
        return ResourceKey.createRegistryKey(rl);
    }

    private static <T> Supplier<Registry<T>> makeRegistry(
        final ResourceKey<Registry<T>> key, Class<T> type, ResourceLocation defaultKey) {
        var builder = new RegistryBuilder<T>(key);
        builder.sync(true);
        builder.defaultKey(defaultKey);
        var captured_ret = builder.create();
        return () -> captured_ret;
    }

}
