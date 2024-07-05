package dtnpaletteofpaws.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import net.minecraft.world.level.block.Block;

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

    public static List<WolfBiomeConfig> getAllWolfBiomeConfigForBiome(RegistryAccess prov, Holder<Biome> biome) {
        var biome_config_reg = prov.registryOrThrow(WolfBiomeConfigs.regKey());
        var filtered_config = biome_config_reg.holders()
            .filter(filter_config -> filter_config
                .value().biomes().contains(biome))
            .map(x -> x.value())
            .collect(Collectors.toList());
        return filtered_config;
    }

    public static Set<Block> getExtraSpawnableBlocksForBiomeConfigs(List<WolfBiomeConfig> configs) {
        var spawnable_block_set = new HashSet<Block>();
        for (var config : configs) {
            var block_list = config.blocks();
            spawnable_block_set.addAll(block_list);
        }
        return spawnable_block_set;
    }

    public static boolean checkCanSpawnInTheDarkForConfigs(List<WolfBiomeConfig> configs) {
        for (var config : configs) {
            if (config.canSpawnInDark())
                return true;
        }
        return false;
    }

    public static List<WolfVariant> getPossibleSpawnVariants(RandomSource random, RegistryAccess prov, Holder<Biome> biome) {
        var filtered_config = getAllWolfBiomeConfigForBiome(prov, biome);

        if (filtered_config.isEmpty())
            return List.of();
        if (filtered_config.size() == 1)
            return new ArrayList<>(filtered_config.get(0).variants());

        int r = random.nextInt(filtered_config.size());
        var selected_config = filtered_config.get(r);

        return new ArrayList<>(selected_config.variants());
    }

}
