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

    public static List<WolfVariant> getAllWolfVariantForBiome(RegistryAccess prov, Holder<Biome> biome) {
        var filtered_config = getAllWolfBiomeConfigForBiome(prov, biome);
        
        var variant_list = new HashSet<WolfVariant>();
        for (var config : filtered_config) {
            variant_list.addAll(config.variants());
        }

        return new ArrayList<>(variant_list);
    }

    public static Set<Block> getExtraSpawnableBlocksForBiome(RegistryAccess prov, Holder<Biome> biome) {
        var config_list = getAllWolfBiomeConfigForBiome(prov, biome);
        var spawnable_block_set = new HashSet<Block>();
        for (var config : config_list) {
            var block_list = config.blocks();
            spawnable_block_set.addAll(block_list);
        }
        return spawnable_block_set;
    }

    public static Optional<WolfVariant> getSpawnVariant(RegistryAccess prov, Holder<Biome> biome, RandomSource random) {
        var variant_list = getAllWolfVariantForBiome(prov, biome);

        if (variant_list.isEmpty())
            return Optional.empty();
        
        if (variant_list.size() == 1)
            return Optional.of(variant_list.get(0));

        int r_index = random.nextInt(variant_list.size());
        return Optional.of(variant_list.get(r_index));
    }

}
