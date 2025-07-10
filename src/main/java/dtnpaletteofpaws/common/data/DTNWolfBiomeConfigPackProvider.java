package dtnpaletteofpaws.common.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.VanillaWolfVariants;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.data.forge_data.ForgeDatapackProviderUtil;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfigs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class DTNWolfBiomeConfigPackProvider {

    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();    
        var prov = event.getLookupProvider();

        var pack_gen = ForgeDatapackProviderUtil.getBuiltinDatapack(gen, event.includeServer(), 
            Constants.MOD_ID, "dtnp_wolf_spawn_rate_inc");
        pack_gen.addProvider(pack_output -> {
            return PackMetadataGenerator.forFeaturePack(pack_output, 
                Component.literal("Increase DTNP Wolf Spawn Rate"));
        });
        pack_gen.addProvider(wolfBiomeConfigDataProvFactory(
            prov, DTNWolfBiomeConfigPackProvider::createWolfSpawnRateIncContent));

        pack_gen = ForgeDatapackProviderUtil.getBuiltinDatapack(gen, event.includeServer(), 
            Constants.MOD_ID, "dtnp_vanilla_variants_spawn");
        pack_gen.addProvider(pack_output -> {
            return PackMetadataGenerator.forFeaturePack(pack_output, 
                Component.literal("Add vanilla's Armored Paws variants as DTNP Wolf Spawn."));
        });
        pack_gen.addProvider(wolfBiomeConfigDataProvFactory(
            prov, DTNWolfBiomeConfigPackProvider::createVanillaVariantSpawnContent));
    }

    public static void createWolfSpawnRateIncContent(BootstapContext<WolfBiomeConfig> ctx) {
        WolfBiomeConfig.builder(ctx, WolfVariants.CHERRY)
            .biome(Biomes.CHERRY_GROVE)
            .spawnChance(0.3f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.LEMONY_LIME)
            .biome(Biomes.BEACH)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.05f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.HIMALAYAN_SALT)
            .biome(Biomes.JAGGED_PEAKS)
            .spawnChance(0.25f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("wolf_bamboo_bamboo"))
            .variant(WolfVariants.BAMBOO)
            .biome(Biomes.BAMBOO_JUNGLE)
            .packSize(1, 3)
            .spawnChance(0.4f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("wolf_bamboo_jungle"))
            .variant(WolfVariants.BAMBOO)
            .biome(Biomes.JUNGLE)
            .packSize(1, 3)
            .spawnChance(0.05f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.CRIMSON)
            .biome(Biomes.CRIMSON_FOREST)
            .extraSpawnableBlock(Blocks.CRIMSON_NYLIUM)
            .canSpawnInDark()
            .spawnChance(0.4f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WARPED)
            .biome(Biomes.WARPED_FOREST)
            .extraSpawnableBlock(Blocks.WARPED_NYLIUM)
            .canSpawnInDark()
            .spawnChance(0.4f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.MOLTEN)
            .biome(Biomes.BASALT_DELTAS)
            .extraSpawnableBlock(
                Blocks.BASALT, Blocks.BLACKSTONE, 
                Blocks.LAVA, Blocks.MAGMA_BLOCK
            )
            .canSpawnInDark()
            .spawnChance(0.4f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BIRCH)
            .biome(Biomes.BIRCH_FOREST)
            .spawnChance(0.1f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.PISTACHIO)
            .biome(Biomes.MANGROVE_SWAMP)
            .extraSpawnableBlock(Blocks.MUD)
            .canSpawnInDark()
            .spawnChance(0.25f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.GUACAMOLE)
            .biome(Biomes.MEADOW)
            .spawnChance(0.2f)
            .buildAndRegister();
        // WolfBiomeConfig.builder(ctx, WolfVariants.YUZU)
        //     .biome(Biomes.SNOWY_BEACH)
        //     .extraSpawnableBlock(Blocks.SAND)
        //     .spawnChance(0.3f)
        //     .buildAndRegister();
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
            .spawnChance(0.15f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WITHERED_SOUL)
            .biome(Biomes.SOUL_SAND_VALLEY)
            .extraSpawnableBlock(Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
            .canSpawnInDark()
            .packSize(1, 3)
            .spawnChance(0.4f)
            .buildAndRegister();
        // WolfBiomeConfig.builder(ctx, Util.getResource("mushroom_pack_config"))
        //     .variant(WolfVariants.MUSHROOM_BROWN, WolfVariants.MUSHROOM_RED)
        //     .biome(Biomes.MUSHROOM_FIELDS)
        //     .extraSpawnableBlock(Blocks.MYCELIUM)
        //     .canSpawnInDark()
        //     .packSize(1, 4)
        //     .spawnChance(0.38f)
        //     .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.BONITO_FLAKES)
            .biome(Biomes.WOODED_BADLANDS)
            .extraSpawnableBlock(Blocks.RED_SAND)
            .spawnChance(0.2f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.KOMBU)
            .biome(Biomes.DESERT)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.1f)
            .packSize(1, 3)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.SHITAKE)
            .biome(Biomes.DARK_FOREST)
            .canSpawnInDark()
            .packSize(1, 2)
            .spawnChance(0.15f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.ENOKI)
            .biome(Biomes.DESERT)
            .extraSpawnableBlock(Blocks.SAND)
            .spawnChance(0.1f)
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
            .spawnChance(0.25f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WALNUT)
            .biome(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
            .packSize(1, 3)
            .spawnChance(0.3f)
            .buildAndRegister();
        // WolfBiomeConfig.builder(ctx, Util.getResource("coral_pack"))
        //     .variant(
        //         WolfVariants.CORAL_BRAIN,
        //         WolfVariants.CORAL_BUBBLE,
        //         WolfVariants.CORAL_FIRE,
        //         WolfVariants.CORAL_HORN,
        //         WolfVariants.CORAL_TUBE
        //     )
        //     .biome(Biomes.WARM_OCEAN)
        //     .extraSpawnableBlock(
        //         Blocks.BRAIN_CORAL_BLOCK,
        //         Blocks.FIRE_CORAL_BLOCK,
        //         Blocks.HORN_CORAL_BLOCK,
        //         Blocks.TUBE_CORAL_BLOCK,
        //         Blocks.BUBBLE_CORAL_BLOCK
        //     )
        //     .waterSpawn()
        //     .canSpawnInDark()
        //     .packSize(1, 3)
        //     .spawnChance(0.9f)
        //     .buildAndRegister();
        // WolfBiomeConfig.builder(ctx, Util.getResource("end_suite"))
        //     .variant(
        //         WolfVariants.ENDER,
        //         WolfVariants.CHORUS
        //     )
        //     .biome(Biomes.END_HIGHLANDS)
        //     .extraSpawnableBlock(Blocks.END_STONE)
        //     .canSpawnInDark()
        //     .spawnChance(0.9f)
        //     .buildAndRegister();
        WolfBiomeConfig.builder(ctx, WolfVariants.WANDERING_SOUL)
            .biome(Biomes.SOUL_SAND_VALLEY)
            .extraSpawnableBlock(Blocks.SOUL_SAND, Blocks.SOUL_SOIL)
            .canSpawnInDark()
            .spawnChance(0.1f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("nether_waste_suite"))
            .variant(
                WolfVariants.SANGUINE,
                WolfVariants.DESICCATED
            )
            .biome(Biomes.NETHER_WASTES)
            .extraSpawnableBlock(Blocks.NETHERRACK)
            .canSpawnInDark()
            .spawnChance(0.1f)
            .buildAndRegister();
        WolfBiomeConfig.builder(ctx, Util.getResource("ice_spike_spawn"))
            .variant(WolfVariants.ICY, WolfVariants.FROSTY)
            .biome(Biomes.ICE_SPIKES)
            .extraSpawnableBlock(Blocks.ICE, Blocks.PACKED_ICE)
            .packSize(1, 2)
            .spawnChance(0.15f)
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
            .spawnChance(0.2f)
            .buildAndRegister();
    }

    public static void createVanillaVariantSpawnContent(BootstapContext<WolfBiomeConfig> ctx) {
        getVanillaBuilder(ctx, VanillaWolfVariants.WOOD)
            .biome(Biomes.FOREST)
            .spawnChance(0.11f)
            .packSize(4, 4)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.ASHEN)
            .biome(Biomes.SNOWY_TAIGA)
            .spawnChance(0.13f)
            .packSize(4, 4)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.RUSTY)
            .biome(Biomes.SPARSE_JUNGLE)
            .spawnChance(0.14f)
            .packSize(2, 4)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.SNOWY)
            .biome(Biomes.GROVE)
            .spawnChance(0.08f)
            .packSize(1, 1)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.SPOTTED)
            .biome(Biomes.SAVANNA_PLATEAU)
            .spawnChance(0.12f)
            .packSize(4, 8)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.BLACK)
            .biome(Biomes.OLD_GROWTH_PINE_TAIGA)
            .spawnChance(0.13f)
            .packSize(4, 4)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.PALE)
            .biome(Biomes.TAIGA)
            .spawnChance(0.13f)
            .packSize(4, 4)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.CHESTNUT)
            .biome(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
            .spawnChance(0.13f)
            .packSize(4, 4)
            .buildAndRegister();
        getVanillaBuilder(ctx, VanillaWolfVariants.STRIPED)
            .biome(Biomes.WOODED_BADLANDS)
            .spawnChance(0.25f)
            .packSize(4, 8)
            .buildAndRegister();
    }

    private static WolfBiomeConfig.Builder getVanillaBuilder(BootstapContext<WolfBiomeConfig> ctx, 
        Supplier<WolfVariant> variant_sup) {
        var variant = variant_sup.get();
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var wolf_variant_id = variant_reg.getKey(variant);
        if (wolf_variant_id == null)
            throw new IllegalStateException("unregistered wolf variant");
        var register_id = Util.getResource("vanilla_" + wolf_variant_id.getPath());
        return WolfBiomeConfig.builder(ctx, register_id).variant(variant);
    }

    public static DataProvider.Factory<DatapackBuiltinEntriesProvider> 
        wolfBiomeConfigDataProvFactory(CompletableFuture<HolderLookup.Provider> prov, 
        RegistryBootstrap<WolfBiomeConfig> bootstrap) {
        
        var wolf_biome_set = new RegistrySetBuilder()
            .add(WolfBiomeConfigs.regKey(), bootstrap);
        
        return (pack_output) -> new DatapackBuiltinEntriesProvider(
                pack_output, prov, 
                wolf_biome_set, Set.of(Constants.MOD_ID) 
        );
    }

}
