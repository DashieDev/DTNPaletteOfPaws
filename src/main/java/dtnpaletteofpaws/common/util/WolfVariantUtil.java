package dtnpaletteofpaws.common.util;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfigs;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class WolfVariantUtil {

    public static WolfVariant getDefault() {
        return WolfVariants.BIRCH.get();
    }
    
    public static WolfVariant variantFromString(String id) {
        var res = ResourceLocation.tryParse(id);
        if (res == null)
            return getDefault();
        var variant = DTNRegistries.DTN_WOLF_VARIANT.get().getValue(res);
        if (variant == null)
            variant = getDefault();
        return variant;
    }

    public static String variantToString(WolfVariant variant) {
        var id = DTNRegistries.DTN_WOLF_VARIANT.get().getKey(variant);
        if (id == null)
            return DTNRegistries.DTN_WOLF_VARIANT.get().getKey(getDefault())
                .toString();
        return id.toString();
    }

    public static WolfVariant getDefaultForSpawn(ServerLevelAccessor s_level_accessor) {
        var level = s_level_accessor.getLevel();
        if (level != null && level.dimension().equals(Level.NETHER))
            return WolfVariants.MOLTEN.get();
        return getDefault();
    }

    public static Optional<WolfVariant> getSpawnVariant(RegistryAccess prov, Holder<Biome> biome, RandomSource random) {
        var biome_config_reg = prov.registryOrThrow(WolfBiomeConfigs.regKey());
        var holders = biome_config_reg.holders();
        var filtered_holders = holders
            .filter(filter_config -> filter_config
                .value().biomes().contains(biome))
            .collect(Collectors.toList());
        if (filtered_holders.isEmpty())
            return Optional.empty();
        
        Holder.Reference<WolfBiomeConfig> selected_holder;
        if (filtered_holders.size() == 1) {
            selected_holder = filtered_holders.get(0);
        } else {
            int r_index = random.nextInt(filtered_holders.size());
            selected_holder = filtered_holders.get(r_index);
        }

        var variant_id = selected_holder.key().location();
        if (variant_id == null)
            return Optional.empty();
        
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var variant = variant_reg.getValue(variant_id);
        if (variant == null)
            return Optional.empty();

        return Optional.of(variant);
    }

}
