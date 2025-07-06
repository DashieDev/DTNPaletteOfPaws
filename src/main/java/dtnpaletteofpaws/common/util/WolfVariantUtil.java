package dtnpaletteofpaws.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

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

    public static WolfVariant getDefaultForSpawn(DTNWolf wolf, ServerLevelAccessor s_level_accessor) {
        var level = s_level_accessor.getLevel();
        if (level != null && level.dimension().equals(Level.NETHER))
            return WolfVariants.MOLTEN.get();
        if (checkWaterSpawn(s_level_accessor, wolf))
            return WolfVariants.KOMBU.get();
        
        return getDefault();
    }

    private static boolean checkWaterSpawn(ServerLevelAccessor s_level_accessor, DTNWolf wolf) {
        var pos = wolf.blockPosition();
        var below_state = s_level_accessor.getFluidState(pos.below());
        return below_state.is(Fluids.WATER);
    }

    public static List<WolfBiomeConfig> getAllWolfBiomeConfigForBiome(RegistryAccess prov, Holder<Biome> biome, 
        Predicate<WolfBiomeConfig> extra_conditions) {
        //Optimize this to use a Map
        var biome_config_reg = prov.registryOrThrow(WolfBiomeConfigs.regKey());
        var filtered_config = biome_config_reg.holders()
            .filter(filter_config -> filter_config
                .value().biomes().contains(biome))
            .map(x -> x.value())
            .filter(extra_conditions)
            .collect(Collectors.toList());
        return filtered_config;
    }

    public static List<WolfVariant> getPossibleSpawnVariants(RandomSource random, RegistryAccess prov, 
        Holder<Biome> biome, Predicate<WolfBiomeConfig> extra_conditions) {
        var filtered_config = getAllWolfBiomeConfigForBiome(prov, biome, extra_conditions);

        if (filtered_config.isEmpty())
            return List.of();
        if (filtered_config.size() == 1)
            return new ArrayList<>(filtered_config.get(0).variants());

        int r = random.nextInt(filtered_config.size());
        var selected_config = filtered_config.get(r);

        return new ArrayList<>(selected_config.variants());
    }

}
