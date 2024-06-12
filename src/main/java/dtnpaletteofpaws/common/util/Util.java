package dtnpaletteofpaws.common.util;

import java.util.function.Function;

import dtnpaletteofpaws.common.lib.Constants;
import net.minecraft.resources.ResourceLocation;

public class Util {
    
    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static ResourceLocation modifyPath(ResourceLocation target, Function<String, String> modifier) {
        var namespace = target.getNamespace();
        var path = target.getPath();
        var new_path = modifier.apply(path);
        return new ResourceLocation(namespace, new_path);
    }

}
