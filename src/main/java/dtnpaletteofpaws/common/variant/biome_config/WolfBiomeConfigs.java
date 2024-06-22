package dtnpaletteofpaws.common.variant.biome_config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class WolfBiomeConfigs {
    
    public static void bootstrap(BootstapContext<WolfBiomeConfig> ctx) {
        register(ctx, WolfVariants.CHERRY.get(), Biomes.CHERRY_GROVE);
        register(ctx, WolfVariants.LEMONY_LIME.get(), Biomes.BEACH, Blocks.SAND);
        register(ctx, WolfVariants.HIMALAYAN_SALT.get(), Biomes.JAGGED_PEAKS);
        register(ctx, WolfVariants.BAMBOO.get(), List.of(Biomes.BAMBOO_JUNGLE, Biomes.JUNGLE));
        register(ctx, WolfVariants.CRIMSON.get(), Biomes.CRIMSON_FOREST, Blocks.CRIMSON_NYLIUM);
        register(ctx, WolfVariants.WARPED.get(), Biomes.WARPED_FOREST, Blocks.WARPED_NYLIUM);
        register(ctx, WolfVariants.MOLTEN.get(), Biomes.BASALT_DELTAS, Blocks.BASALT);
        register(ctx, WolfVariants.BIRCH.get(), Biomes.BIRCH_FOREST);
        register(ctx, WolfVariants.PISTACHIO.get(), Biomes.MANGROVE_SWAMP, Blocks.MUD);
        register(ctx, WolfVariants.GUACAMOLE.get(), Biomes.MEADOW);
        register(ctx, WolfVariants.YUZU.get(), Biomes.SNOWY_BEACH, Blocks.SAND);
        register(ctx, 
            List.of(
                WolfVariants.CAPPUCCINO.get(),
                WolfVariants.LATTE.get(),
                WolfVariants.MOCHA.get(),
                WolfVariants.ESPRESSO.get()
            ), Util.getResource("caffeine_pack_config"), Biomes.DARK_FOREST, List.of());
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, List<ResourceKey<Biome>> biomes) {

        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_holders_list = biomes.stream()
            .map(x -> biome_reg.get(x))
            .filter(x -> x.isPresent())
            .map(x -> x.get())
            .collect(Collectors.toList());
        var biome_holder_set = HolderSet.direct(biome_holders_list);
        
        register(ctx, wolf_variant, biome_holder_set, List.of());
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, ResourceKey<Biome> biome, Block block) {

        register(ctx, wolf_variant, biome, List.of(block));
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, ResourceKey<Biome> biome) {

        register(ctx, wolf_variant, biome, List.of());
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, ResourceKey<Biome> biome, List<Block> spawnable_blocks) {

        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_set = HolderSet.direct(biome_reg.getOrThrow(biome));

        register(ctx, wolf_variant, biome_set, spawnable_blocks);
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, HolderSet<Biome> biomes, List<Block> spawnable_blocks) {
        
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var wolf_variant_id = variant_reg.getKey(wolf_variant);
        if (wolf_variant_id == null)
            return;
        register(ctx, List.of(wolf_variant), wolf_variant_id, biomes, spawnable_blocks);
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, List<WolfVariant> variants,
        ResourceLocation id, ResourceKey<Biome> biome, List<Block> spawnable_blocks) {
            
        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_set = HolderSet.direct(biome_reg.getOrThrow(biome));

        register(ctx, variants, id, biome_set, spawnable_blocks);
    }

    public static void register(BootstapContext<WolfBiomeConfig> ctx, List<WolfVariant> variants,
        ResourceLocation id, HolderSet<Biome> biomes, List<Block> spawnable_blocks) {
            
        var res_key = ResourceKey.create(WolfBiomeConfigs.regKey(), id);
        var config = new WolfBiomeConfig(variants, biomes, spawnable_blocks);

        ctx.register(res_key, config);
    }
    
    public static ResourceKey<Registry<WolfBiomeConfig>> regKey() {
        return DTNRegistries.DataKeys.WOLF_BIOME_CONFIG;
    }

}
