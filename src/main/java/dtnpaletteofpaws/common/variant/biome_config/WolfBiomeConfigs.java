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
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WolfBiomeConfigs {
    
    public static void bootstrap(BootstrapContext<WolfBiomeConfig> ctx) {
        WolfBiomeConfig.builder(ctx, WolfVariants.CHERRY)
            .biome(Biomes.CHERRY_GROVE)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.LEMONY_LIME)
            .biome(Biomes.BEACH)
            .extraSpawnableBlock(Blocks.SAND)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.HIMALAYAN_SALT)
            .biome(Biomes.JAGGED_PEAKS)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BAMBOO)
            .biomes(List.of(Biomes.BAMBOO_JUNGLE, Biomes.JUNGLE))
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.CRIMSON)
            .biome(Biomes.CRIMSON_FOREST)
            .extraSpawnableBlock(Blocks.CRIMSON_NYLIUM)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WARPED)
            .biome(Biomes.WARPED_FOREST)
            .extraSpawnableBlock(Blocks.WARPED_NYLIUM)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.MOLTEN)
            .biome(Biomes.BASALT_DELTAS)
            .extraSpawnableBlock(Blocks.BASALT)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BIRCH)
            .biome(Biomes.BIRCH_FOREST)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.PISTACHIO)
            .biome(Biomes.MANGROVE_SWAMP)
            .extraSpawnableBlock(Blocks.MUD)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.GUACAMOLE)
            .biome(Biomes.MEADOW)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.YUZU)
            .biome(Biomes.SNOWY_BEACH)
            .extraSpawnableBlock(Blocks.SAND)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("caffeine_pack_config"))
            .variants(List.of(
                WolfVariants.CAPPUCCINO.get(),
                WolfVariants.LATTE.get(),
                WolfVariants.MOCHA.get(),
                WolfVariants.ESPRESSO.get()
            ))
            .biome(Biomes.DARK_FOREST)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WITHERED_SOUL)
            .biome(Biomes.SOUL_SAND_VALLEY)
            .extraSpawnableBlocks(List.of(Blocks.SOUL_SAND, Blocks.SOUL_SOIL))
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("mushroom_pack_config"))
            .variants(List.of(
                WolfVariants.MUSHROOM_BROWN.get(),
                WolfVariants.MUSHROOM_RED.get()
            ))
            .biome(Biomes.MUSHROOM_FIELDS)
            .extraSpawnableBlock(Blocks.MYCELIUM)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BONITO_FLAKES)
            .biome(Biomes.WOODED_BADLANDS)
            .extraSpawnableBlock(Blocks.RED_SAND)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.KOMBU)
            .biome(Biomes.DESERT)
            .extraSpawnableBlock(Blocks.SAND)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.SHITAKE)
            .biome(Biomes.DARK_FOREST)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.ENOKI)
            .biome(Biomes.DESERT)
            .extraSpawnableBlock(Blocks.SAND)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("gelato_suite"))
            .variants(List.of(
                WolfVariants.CHOCOLATE.get(),
                WolfVariants.STRAWBERRY.get(),
                WolfVariants.VANILLA.get(),
                WolfVariants.CAKE.get()
            ))
            .biome(Biomes.SNOWY_PLAINS)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WALNUT)
            .biome(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("coral_pack"))
            .variants(List.of(
                WolfVariants.CORAL_BRAIN.get(),
                WolfVariants.CORAL_BUBBLE.get(),
                WolfVariants.CORAL_FIRE.get(),
                WolfVariants.CORAL_HORN.get(),
                WolfVariants.CORAL_TUBE.get()
            ))
            .biome(Biomes.WARM_OCEAN)
            .extraSpawnableBlocks(List.of(
                Blocks.BRAIN_CORAL_BLOCK,
                Blocks.FIRE_CORAL_BLOCK,
                Blocks.HORN_CORAL_BLOCK,
                Blocks.TUBE_CORAL_BLOCK,
                Blocks.BUBBLE_CORAL_BLOCK
            ))
            .waterSpawn()
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("end_suite"))
            .variants(List.of(
                WolfVariants.ENDER.get(),
                WolfVariants.CHORUS.get()
            ))
            .biome(Biomes.END_HIGHLANDS)
            .extraSpawnableBlock(Blocks.END_STONE)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WANDERING_SOUL)
            .biome(Biomes.SOUL_SAND_VALLEY)
            .extraSpawnableBlocks(List.of(Blocks.SOUL_SAND, Blocks.SOUL_SOIL))
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("nether_waste_suite"))
            .variants(List.of(
                WolfVariants.SANGUINE.get(),
                WolfVariants.DESICCATED.get()
            ))
            .biome(Biomes.NETHER_WASTES)
            .extraSpawnableBlock(Blocks.NETHERRACK)
            .canSpawnInDark()
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("ice_spike_spawn"))
            .variants(List.of(WolfVariants.ICY.get(), WolfVariants.FROSTY.get()))
            .biome(Biomes.ICE_SPIKES)
            .extraSpawnableBlocks(List.of(Blocks.ICE, Blocks.PACKED_ICE))
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("savanna_suite"))
            .variants(List.of(
                WolfVariants.SOOTY.get(),
                WolfVariants.BRINDLE.get(),
                WolfVariants.MUDDY.get(),
                WolfVariants.ROOT_BEER.get()
            ))
            .biome(Biomes.SAVANNA)
            .canSpawnInDark()
            .buildAndRegister();
    }
    
    public static ResourceKey<Registry<WolfBiomeConfig>> regKey() {
        return DTNRegistries.DataKeys.WOLF_BIOME_CONFIG;
    }

}
