package dtnpaletteofpaws.common.util;

import dtnpaletteofpaws.PawsRegistries;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class WolfVariantUtil {
    
    public static WolfVariant getOrDefault(String id) {
        var res = ResourceLocation.tryParse(id);
        if (res == null)
            return WolfVariants.MOLTEN_WOLF.get();
        var variant = PawsRegistries.DTN_WOLF_VARIANT.get().getValue(res);
        if (variant == null)
            variant = WolfVariants.MOLTEN_WOLF.get();
        return variant;
    }

    public static WolfVariant getSpawnVariant(Holder<Biome> biome) {
        return WolfVariants.MOLTEN_WOLF.get();
    }

}
