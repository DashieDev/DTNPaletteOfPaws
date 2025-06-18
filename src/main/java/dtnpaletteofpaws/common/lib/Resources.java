package dtnpaletteofpaws.common.lib;

import dtnpaletteofpaws.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class Resources {

    public static final ResourceLocation DTNP_PACK_ICON = Util.getResource("dtnp_pack_icon.png");

    public static ResourceLocation getDTNWolfTexture(String name) {
        var prefix = "textures/entity/dtnwolf/";
        return Util.getResource(prefix + name + ".png");
    }
    
}
