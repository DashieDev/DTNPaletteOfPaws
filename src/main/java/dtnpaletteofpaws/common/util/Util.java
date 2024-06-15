package dtnpaletteofpaws.common.util;

import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import dtnpaletteofpaws.common.lib.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class Util {
    
    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static <T> ResourceKey<Registry<T>> getRegistryKey(Class<T> type, String name) {
        return ResourceKey.<T>createRegistryKey(getResource(name));
    }

    public static ResourceLocation modifyPath(ResourceLocation target, Function<String, String> modifier) {
        var namespace = target.getNamespace();
        var path = target.getPath();
        var new_path = modifier.apply(path);
        return new ResourceLocation(namespace, new_path);
    }

    public static <T> Codec<T> deferredCodec(Supplier<Codec<T>> value_sup) {
        return ExtraCodecs.lazyInitializedCodec(value_sup);
    }

}
