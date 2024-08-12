package dtnpaletteofpaws.common.spawn;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import dtnpaletteofpaws.DTNEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class DTNWolfVariantsFabricSpawn {

    public static boolean isSpawnPositionOk_imitate(SpawnPlacements type, LevelReader level, BlockPos pos, EntityType<?> entity_type) {
        if (!level.getWorldBorder().isWithinBounds(pos)) {
            return false;
        }
        BlockState spawn_state = level.getBlockState(pos);
        FluidState spawn_fluid = level.getFluidState(pos);
        BlockPos pos_above = pos.above();
        BlockPos pos_below = pos.below();
        // switch (type) {
        //     case IN_WATER: {
        //         return spawn_fluid.is(FluidTags.WATER) && !level.getBlockState(pos_above).isRedstoneConductor(level, pos_above);
        //     }
        //     case IN_LAVA: {
        //         return spawn_fluid.is(FluidTags.LAVA);
        //     }
        // }
        BlockState below_state = level.getBlockState(pos_below);
        if (!below_state.isValidSpawn(level, pos_below, entity_type)) {
            return false;
        }

        boolean is_empty_spawn_block = 
            NaturalSpawner.isValidEmptySpawnBlock(level, pos, spawn_state, spawn_fluid, entity_type);
        if (!is_empty_spawn_block)
            return false;
        boolean is_empty_above_block =
            NaturalSpawner.isValidEmptySpawnBlock(level, pos_above, level.getBlockState(pos_above), level.getFluidState(pos_above), entity_type);
        if (!is_empty_above_block)
            return false;
        return true;
    }
    
    private static Predicate<BiomeSelectionContext> selectForKey(ResourceKey<Biome> biomeKey) {
        return ctx -> ctx.getBiomeKey().equals(biomeKey);
    }

    public static void init() {
        registerCherryWolfModifier();
        registerLemonyLimeWolfModifier();
        registerHimalayanSaltWolfModifier();
        registerBambooWolfModifier();
        registerCrimsonWolfModifier();
        registerWarpedWolfModifier();
        registerMoltenWolfModifier();
        registerBirchWolfModifier();
        registerPistachioWolfModifier();
        registerGuacamoleWolfModifier();
        registerYuzuWolfModifier();
        registerCaffeinePackModifier();
        registerWitheredSoulWolfModifier();
        registerMushroomPackModifier();
        registerDesertSuiteModifier();
        registerBonitoFlakesWolfModifier();
        registerGelatoSuiteModifier();
        registerWalnutWolfModifier();
        registerKombuWolfWaterModifier();
    }

    private static void registerCherryWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.CHERRY_GROVE),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerLemonyLimeWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.BEACH),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerHimalayanSaltWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.JAGGED_PEAKS),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerBambooWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.JUNGLE),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
        BiomeModifications.addSpawn(selectForKey(Biomes.BAMBOO_JUNGLE),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 60, 1, 1);
    }

    private static void registerCrimsonWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.CRIMSON_FOREST),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 20, 1, 1);
    }

    private static void registerWarpedWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.WARPED_FOREST),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 20, 1, 1);
    }

    private static void registerMoltenWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.BASALT_DELTAS),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 20, 1, 1);
    }

    private static void registerBirchWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.BIRCH_FOREST),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerPistachioWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.MANGROVE_SWAMP),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerGuacamoleWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.MEADOW),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerYuzuWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.SNOWY_BEACH),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerCaffeinePackModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.DARK_FOREST),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 4, 2, 4);
    }

    private static void registerWitheredSoulWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.SOUL_SAND_VALLEY),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerMushroomPackModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.MUSHROOM_FIELDS),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 5, 2, 4);
    }

    private static void registerDesertSuiteModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.DESERT),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerBonitoFlakesWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.WOODED_BADLANDS),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }

    private static void registerGelatoSuiteModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.SNOWY_PLAINS),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 3);
    }

    private static void registerWalnutWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 3);
    }

    private static void registerKombuWolfWaterModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.LUKEWARM_OCEAN),
            MobCategory.CREATURE, DTNEntityTypes.DTNWOLF.get(), 1, 1, 1);
    }
}