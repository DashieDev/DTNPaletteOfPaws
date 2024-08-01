package dtnpaletteofpaws.common.variant.biome_config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.checkerframework.checker.units.qual.m;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WolfBiomeConfigs {
    
    public static void bootstrap(BootstrapContext<WolfBiomeConfig> ctx) {
        register(ctx, WolfVariants.CHERRY.get(), Biomes.CHERRY_GROVE, false);
        register(ctx, WolfVariants.LEMONY_LIME.get(), Biomes.BEACH, Blocks.SAND, false);
        register(ctx, WolfVariants.HIMALAYAN_SALT.get(), Biomes.JAGGED_PEAKS, false);
        register(ctx, WolfVariants.BAMBOO.get(), List.of(Biomes.BAMBOO_JUNGLE, Biomes.JUNGLE), false);
        register(ctx, WolfVariants.CRIMSON.get(), Biomes.CRIMSON_FOREST, Blocks.CRIMSON_NYLIUM, true);
        register(ctx, WolfVariants.WARPED.get(), Biomes.WARPED_FOREST, Blocks.WARPED_NYLIUM, true);
        register(ctx, WolfVariants.MOLTEN.get(), Biomes.BASALT_DELTAS, Blocks.BASALT, true);
        register(ctx, WolfVariants.BIRCH.get(), Biomes.BIRCH_FOREST, false);
        register(ctx, WolfVariants.PISTACHIO.get(), Biomes.MANGROVE_SWAMP, Blocks.MUD, true);
        register(ctx, WolfVariants.GUACAMOLE.get(), Biomes.MEADOW, false);
        register(ctx, WolfVariants.YUZU.get(), Biomes.SNOWY_BEACH, Blocks.SAND, false);
        register(ctx, 
            List.of(
                WolfVariants.CAPPUCCINO.get(),
                WolfVariants.LATTE.get(),
                WolfVariants.MOCHA.get(),
                WolfVariants.ESPRESSO.get()
            ), Util.getResource("caffeine_pack_config"), Biomes.DARK_FOREST, List.of(), true);
        register(ctx, WolfVariants.WITHERED_SOUL.get(), Biomes.SOUL_SAND_VALLEY, List.of(Blocks.SOUL_SAND, Blocks.SOUL_SOIL), true);
        register(ctx, List.of(
            WolfVariants.MUSHROOM_BROWN.get(),
            WolfVariants.MUSHROOM_RED.get()
        ), Util.getResource("mushroom_pack_config"), Biomes.MUSHROOM_FIELDS, List.of(Blocks.MYCELIUM), true);
        register(ctx, WolfVariants.BONITO_FLAKES.get(), Biomes.WOODED_BADLANDS, List.of(Blocks.RED_SAND), false);
        register(ctx, WolfVariants.KOMBU.get(), Biomes.DESERT, List.of(Blocks.SAND), false);
        register(ctx, WolfVariants.SHITAKE.get(), Biomes.DARK_FOREST, true);
        register(ctx, WolfVariants.ENOKI.get(), Biomes.DESERT, false);
        register(ctx, List.of(
            WolfVariants.CHOCOLATE.get(),
            WolfVariants.STRAWBERRY.get(),
            WolfVariants.VANILLA.get()
        ), Util.getResource("gelato_suite"), Biomes.SNOWY_PLAINS, List.of(), true);
        register(ctx, WolfVariants.WALNUT.get(), Biomes.OLD_GROWTH_SPRUCE_TAIGA, false);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, List<ResourceKey<Biome>> biomes, boolean can_spawn_in_dark) {

        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_holders_list = biomes.stream()
            .map(x -> biome_reg.get(x))
            .filter(x -> x.isPresent())
            .map(x -> x.get())
            .collect(Collectors.toList());
        var biome_holder_set = HolderSet.direct(biome_holders_list);
        
        register(ctx, wolf_variant, biome_holder_set, List.of(), can_spawn_in_dark);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, ResourceKey<Biome> biome, Block block, boolean can_spawn_in_dark) {

        register(ctx, wolf_variant, biome, List.of(block), can_spawn_in_dark);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, ResourceKey<Biome> biome, boolean can_spawn_in_dark) {

        register(ctx, wolf_variant, biome, List.of(), can_spawn_in_dark);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, ResourceKey<Biome> biome, List<Block> spawnable_blocks, boolean can_spawn_in_dark) {

        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_set = HolderSet.direct(biome_reg.getOrThrow(biome));

        register(ctx, wolf_variant, biome_set, spawnable_blocks, can_spawn_in_dark);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, 
        WolfVariant wolf_variant, HolderSet<Biome> biomes, List<Block> spawnable_blocks,
        boolean can_spawn_in_dark) {
        
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var wolf_variant_id = variant_reg.getKey(wolf_variant);
        if (wolf_variant_id == null)
            return;
        register(ctx, List.of(wolf_variant), wolf_variant_id, biomes, spawnable_blocks, can_spawn_in_dark);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, List<WolfVariant> variants,
        ResourceLocation id, ResourceKey<Biome> biome, List<Block> spawnable_blocks, boolean can_spawn_in_dark) {
            
        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_set = HolderSet.direct(biome_reg.getOrThrow(biome));

        register(ctx, variants, id, biome_set, spawnable_blocks, can_spawn_in_dark);
    }

    public static void register(BootstrapContext<WolfBiomeConfig> ctx, List<WolfVariant> variants,
        ResourceLocation id, HolderSet<Biome> biomes, List<Block> spawnable_blocks, boolean can_spawn_in_dark) {
            
        var res_key = ResourceKey.create(WolfBiomeConfigs.regKey(), id);
        var config = new WolfBiomeConfig(variants, biomes, spawnable_blocks, can_spawn_in_dark);

        ctx.register(res_key, config);
    }
    
    public static ResourceKey<Registry<WolfBiomeConfig>> regKey() {
        return DTNRegistries.DataKeys.WOLF_BIOME_CONFIG;
    }

}
