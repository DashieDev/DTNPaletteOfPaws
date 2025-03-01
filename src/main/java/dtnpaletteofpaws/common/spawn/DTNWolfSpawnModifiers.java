package dtnpaletteofpaws.common.spawn;

import java.util.List;
import java.util.stream.Collectors;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DTNWolfSpawnModifiers {
    
    public static void bootstrap(BootstrapContext<BiomeModifier> ctx) {
        registerWolfModifier(ctx);
    }

    private static void registerWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerCherryWolfModifier(ctx);
        registerLemonyLimeWolfModifier(ctx);
        registerHimalayanSaltWolfModifier(ctx);
        registerBambooWolfModifier(ctx);
        registerCrimsonWolfModifier(ctx);
        registerWarpedWolfModifier(ctx);
        registerMoltenWolfModifier(ctx);
        registerBirchWolfModifier(ctx);
        registerPistachioWolfModifier(ctx);
        registerGuacamoleWolfModifier(ctx);
        registerYuzuWolfModifier(ctx);
        registerCaffeinePackModifier(ctx);
        registerWitheredSoulWolfModifier(ctx);
        registerMushroomPackModifier(ctx);
        registerDesertSuiteModifier(ctx);
        registerBonitoFlakesWolfModifier(ctx);
        registerGelatoSuiteModifier(ctx);
        registerWalnutWolfModifier(ctx);
        //registerKombuWolfWaterModifier(ctx);
        registerCoralWolfSpawnModifier(ctx);
        registerNetherWasteSuite(ctx);
        registerIcySpawnModifier(ctx);
        registerSavannaSpawnModifier(ctx);
    }

    private static void registerCherryWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_cherry", 
            Biomes.CHERRY_GROVE, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerLemonyLimeWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_lemony_lime", 
            Biomes.BEACH, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerHimalayanSaltWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_himalayan_salt", 
            Biomes.JAGGED_PEAKS, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerBambooWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_bamboo", 
            Biomes.JUNGLE, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
        registerSingleSpawnModifier(
            ctx, "wolf_bamboo_dedicated", 
            Biomes.BAMBOO_JUNGLE,
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 60, 1, 3)
        );
    }

    private static void registerCrimsonWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_crimson", 
            Biomes.CRIMSON_FOREST, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 20, 1, 1)
        );
    }

    private static void registerWarpedWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_warped", 
            Biomes.WARPED_FOREST, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 20, 1, 1)
        );
    }

    private static void registerMoltenWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_molten", 
            Biomes.BASALT_DELTAS, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 20, 1, 1)
        );
    }

    private static void registerBirchWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_birch", 
            Biomes.BIRCH_FOREST, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerPistachioWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_pistachio", 
            Biomes.MANGROVE_SWAMP, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerGuacamoleWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_guacamole", 
            Biomes.MEADOW, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerYuzuWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_yuzu", 
            Biomes.SNOWY_BEACH, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerCaffeinePackModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "dark_forest_suite", 
            Biomes.DARK_FOREST, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 4, 2, 4)
        );
    }

    private static void registerWitheredSoulWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_withered_soul", 
            Biomes.SOUL_SAND_VALLEY, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerMushroomPackModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "mushroom_pack", 
            Biomes.MUSHROOM_FIELDS, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 5, 2, 4)
        );
    }

    private static void registerDesertSuiteModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "desert_suite", 
            Biomes.DESERT, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerBonitoFlakesWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_bonito_flakes", 
            Biomes.WOODED_BADLANDS, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerGelatoSuiteModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "gelato_suite", 
            Biomes.SNOWY_PLAINS, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 3)
        );
    }

    private static void registerWalnutWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_walnut", 
            Biomes.OLD_GROWTH_SPRUCE_TAIGA, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 3)
        );
    }

    private static void registerKombuWolfWaterModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_kombu_waterspawn", 
            Biomes.LUKEWARM_OCEAN, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerCoralWolfSpawnModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_coral_pack_spawn", 
            Biomes.WARM_OCEAN, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 3)
        );
    }

    private static void registerNetherWasteSuite(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "nether_waste_suite", 
            Biomes.NETHER_WASTES, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 1)
        );
    }

    private static void registerIcySpawnModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_icy_spawn", 
            Biomes.ICE_SPIKES, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 1, 1, 4)
        );
    }

    private static void registerSavannaSpawnModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_savanna_spawn", 
            Biomes.SAVANNA, 
            new MobSpawnSettings
                .SpawnerData(DTNEntityTypes.DTNWOLF.get(), 15, 1, 4)
        );
    }

    private static void registerSingleSpawnModifier(BootstrapContext<BiomeModifier> ctx,
        String name, ResourceKey<Biome> biome, MobSpawnSettings.SpawnerData spawner_data) {
        
        registerSingleSpawnModifier(ctx, name, List.of(biome), spawner_data);
    }

    private static void registerSingleSpawnModifier(BootstrapContext<BiomeModifier> ctx,
        String name, List<ResourceKey<Biome>> biomes, MobSpawnSettings.SpawnerData spawner_data) {
        
        var spawn_id = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
            Util.getResource(name));
        
        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_holders = biomes.stream()
            .map(x -> biome_reg.get(x))
            .filter(x -> x.isPresent())
            .map(x -> x.get())
            .collect(Collectors.toList());
        if (biome_holders.isEmpty())
            return;
        var spawn_biomes = HolderSet.direct(biome_holders);
        var spawn_modifier = BiomeModifiers.AddSpawnsBiomeModifier
            .singleSpawn(spawn_biomes, spawner_data);
        
        ctx.register(spawn_id, spawn_modifier);
    }

}
