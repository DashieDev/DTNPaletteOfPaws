package dtnpaletteofpaws.common.variant.biome_config;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.spawn.WolfSpawnPlacementType;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class WolfBiomeConfigs {
    
    public static void bootstrap(BootstapContext<WolfBiomeConfig> ctx) {
        WolfBiomeConfig.builder(ctx, WolfVariants.CHERRY)
            .biome(Biomes.CHERRY_GROVE)
            .spawnChance(0.17f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.LEMONY_LIME)
            .biome(Biomes.BEACH)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.05f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.HIMALAYAN_SALT)
            .biome(Biomes.JAGGED_PEAKS)
            .spawnChance(0.17f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("wolf_bamboo_bamboo"))
            .variant(WolfVariants.BAMBOO)
            .biome(Biomes.BAMBOO_JUNGLE)
            .packSize(1, 3)
            .spawnChance(0.26f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("wolf_bamboo_jungle"))
            .variant(WolfVariants.BAMBOO)
            .biome(Biomes.JUNGLE)
            .packSize(1, 3)
            .spawnChance(0.01f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.CRIMSON)
            .biome(Biomes.CRIMSON_FOREST)
            .extraSpawnableBlock(Blocks.CRIMSON_NYLIUM)
            .canSpawnInDark()
            .spawnChance(0.25f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WARPED)
            .biome(Biomes.WARPED_FOREST)
            .extraSpawnableBlock(Blocks.WARPED_NYLIUM)
            .canSpawnInDark()
            .spawnChance(0.25f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.MOLTEN)
            .biome(Biomes.BASALT_DELTAS)
            .extraSpawnableBlock(Blocks.BASALT)
            .canSpawnInDark()
            .spawnChance(0.25f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BIRCH)
            .biome(Biomes.BIRCH_FOREST)
            .spawnChance(0.07f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.PISTACHIO)
            .biome(Biomes.MANGROVE_SWAMP)
            .extraSpawnableBlock(Blocks.MUD)
            .canSpawnInDark()
            .spawnChance(0.1f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.GUACAMOLE)
            .biome(Biomes.MEADOW)
            .spawnChance(0.07f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.YUZU)
            .biome(Biomes.SNOWY_BEACH)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.3f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("caffeine_pack_config"))
            .variant(
                WolfVariants.CAPPUCCINO,
                WolfVariants.LATTE,
                WolfVariants.MOCHA,
                WolfVariants.ESPRESSO
            )
            .biome(Biomes.DARK_FOREST)
            .canSpawnInDark()
            .packSize(1, 4)
            .spawnChance(0.09f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WITHERED_SOUL)
            .biome(Biomes.SOUL_SAND_VALLEY)
            .extraSpawnableBlock(Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
            .canSpawnInDark()
            .spawnChance(0.1f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("mushroom_pack_config"))
            .variant(WolfVariants.MUSHROOM_BROWN, WolfVariants.MUSHROOM_RED)
            .biome(Biomes.MUSHROOM_FIELDS)
            .extraSpawnableBlock(Blocks.MYCELIUM)
            .canSpawnInDark()
            .packSize(1, 4)
            .spawnChance(0.38f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BONITO_FLAKES)
            .biome(Biomes.WOODED_BADLANDS)
            .extraSpawnableBlock(Blocks.RED_SAND)
            .spawnChance(0.11f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.KOMBU)
            .biome(Biomes.DESERT)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.05f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.SHITAKE)
            .biome(Biomes.DARK_FOREST)
            .canSpawnInDark()
            .packSize(1, 2)
            .spawnChance(0.09f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.ENOKI)
            .biome(Biomes.DESERT)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.05f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("gelato_suite"))
            .variant(
                WolfVariants.CHOCOLATE,
                WolfVariants.STRAWBERRY,
                WolfVariants.VANILLA,
                WolfVariants.CAKE
            )
            .biome(Biomes.SNOWY_PLAINS)
            .canSpawnInDark()
            .packSize(1, 3)
            .spawnChance(0.15f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WALNUT)
            .biome(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
            .packSize(1, 3)
            .spawnChance(0.2f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("coral_pack"))
            .variant(
                WolfVariants.CORAL_BRAIN,
                WolfVariants.CORAL_BUBBLE,
                WolfVariants.CORAL_FIRE,
                WolfVariants.CORAL_HORN,
                WolfVariants.CORAL_TUBE
            )
            .biome(Biomes.WARM_OCEAN)
            .extraSpawnableBlock(
                Blocks.BRAIN_CORAL_BLOCK,
                Blocks.FIRE_CORAL_BLOCK,
                Blocks.HORN_CORAL_BLOCK,
                Blocks.TUBE_CORAL_BLOCK,
                Blocks.BUBBLE_CORAL_BLOCK
            )
            .placementType(WolfSpawnPlacementType.WATER)
            .canSpawnInDark()
            .packSize(1, 3)
            .spawnChance(0.9f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("end_suite"))
            .variant(
                WolfVariants.ENDER,
                WolfVariants.CHORUS
            )
            .biome(Biomes.END_HIGHLANDS)
            .extraSpawnableBlock(Blocks.END_STONE)
            .canSpawnInDark()
            .spawnChance(0.9f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WANDERING_SOUL)
            .biome(Biomes.SOUL_SAND_VALLEY)
            .extraSpawnableBlock(Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
            .canSpawnInDark()
            .spawnChance(0.02f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("nether_waste_suite"))
            .variant(
                WolfVariants.SANGUINE,
                WolfVariants.DESICCATED
            )
            .biome(Biomes.NETHER_WASTES)
            .extraSpawnableBlock(Blocks.NETHERRACK)
            .canSpawnInDark()
            .spawnChance(0.02f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("ice_spike_spawn"))
            .variant(WolfVariants.ICY, WolfVariants.FROSTY)
            .biome(Biomes.ICE_SPIKES)
            .extraSpawnableBlock(Blocks.ICE, Blocks.PACKED_ICE)
            .packSize(1, 2)
            .spawnChance(0.08f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("savanna_suite"))
            .variant(
                WolfVariants.SOOTY,
                WolfVariants.BRINDLE,
                WolfVariants.MUDDY,
                WolfVariants.ROOT_BEER
            )
            .biome(Biomes.SAVANNA)
            .canSpawnInDark()
            .packSize(1, 4)
            .spawnChance(0.1f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.CHARCOAL)
            .biome(Biomes.SAVANNA)
            .canSpawnInDark()
            .placementType(WolfSpawnPlacementType.UNDERGROUND)
            .extraSpawnableBlock(Blocks.DEEPSLATE)
            .packSize(1, 2)
            .spawnChance(0.1f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.SCULK)
            .biome(
                Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS, 
                Biomes.STONY_PEAKS, Biomes.MEADOW
            )
            .canSpawnInDark()
            .placementType(WolfSpawnPlacementType.UNDERGROUND)
            .extraSpawnableBlock(Blocks.DEEPSLATE, Blocks.SCULK)
            .spawnChance(0.1f)
            .buildAndRegister();
    }
    
    public static ResourceKey<Registry<WolfBiomeConfig>> regKey() {
        return DTNRegistries.DataKeys.WOLF_BIOME_CONFIG;
    }

}
