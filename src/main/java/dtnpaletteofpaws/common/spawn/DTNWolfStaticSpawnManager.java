package dtnpaletteofpaws.common.spawn;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.util.RandomUtil;
import dtnpaletteofpaws.common.util.WolfVariantUtil;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class DTNWolfStaticSpawnManager {
    
    private static final DTNWolfStaticSpawnManager instance = new DTNWolfStaticSpawnManager();  
    private static final ThreadLocal<Holder<Biome>> currentSpawnBiome = new ThreadLocal<>();

    public static DTNWolfStaticSpawnManager get() {
        return instance;
    }

    public void onChunkGenerationMobSpawn(ServerLevelAccessor level_accessor, Holder<Biome> biome, ChunkPos chunk_pos, RandomSource random) {
        //do DTNP wolf spawn here to avoid getBioemAagain
        //currentSpawnBiome.set(biome);
        
        var spawn_settings = biome.value().getMobSettings();
        List<WolfBiomeConfig> configs = null;
        while (random.nextFloat() < spawn_settings.getCreatureProbability()) {
            if (configs == null) {
                configs = WolfVariantUtil.getAllWolfBiomeConfigForBiome(level_accessor.registryAccess(), biome);
                if (configs.isEmpty())
                    break;
            }

            var filtered_configs = filterWolfBiomeConfig(configs, random);
            if (filtered_configs.isEmpty())
                continue;
            
            var config = RandomUtil.getRandomItem(random, filtered_configs).orElse(null);
            if (config == null)
                continue;
            
            doChunkGeneratedSpawnIteration(level_accessor, biome, chunk_pos, random, config);
        }
    }

    public static List<WolfBiomeConfig> filterWolfBiomeConfig(List<WolfBiomeConfig> configs, RandomSource random) {
        final float r = random.nextFloat();
        return configs
            .stream().filter(x -> r <= x.spawnChance()).toList();
    }

    //TODO Invalidate currentSpawnBiome when return
    //TODO also note the getBiome consistency accross everytime a biome-check when DTNWolfSpawn
    //to check based on this to be appropriate

    public Optional<Holder<Biome>> currentSpawnBiome() {
        var val = currentSpawnBiome.get();
        return Optional.ofNullable(val);
    }

    public static void onSpawnOriginalMobsForChunk(WorldGenRegion region) {
        if (isTheEndSpawn(region)) {
            spawnDTNWOlfForChunkGeneration(region, 0.01f, region.getMaxBuildHeight() - 1);
        }
    }

    private static boolean isTheEndSpawn(WorldGenRegion region) {
        var level = region.getLevel();
        if (level == null)
            return false;
        var dim = level.dimension();
        if (dim == null)
            return false;
        if (!dim.equals(Level.END))
            return false;
        
        return true;
    }

    public static void spawnDTNWOlfForChunkGeneration(WorldGenRegion region, float biome_chance, int at_height) {
        var chunkpos = region.getCenter();
        var biome = region.getBiome(chunkpos.getWorldPosition().atY(at_height));
        var worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
        worldgenrandom.setDecorationSeed(region.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
        spawnDTNWOlfForChunkGeneration(region, biome, chunkpos, worldgenrandom, biome_chance);
    }

    public static void spawnDTNWOlfForChunkGeneration(ServerLevelAccessor level_accessor, Holder<Biome> biome, 
        ChunkPos chunk_pos, RandomSource rand, float biome_chance) {
        get().onChunkGenerationMobSpawn(level_accessor, biome, chunk_pos, rand);
    }

    private static void doChunkGeneratedSpawnIteration(ServerLevelAccessor level_accessor, Holder<Biome> biome, 
        ChunkPos chunk_pos, RandomSource rand, WolfBiomeConfig config) {
        int min_x = chunk_pos.getMinBlockX();
        int min_z = chunk_pos.getMinBlockZ();
        int spawn_count = config.minCount()
            + rand.nextInt(1 + config.maxCount() - config.minCount());
        var spawngroupdata = new MutableObject<SpawnGroupData>(null);
        int check_x = min_x + rand.nextInt(16);
        int check_z = min_z + rand.nextInt(16);

        for (int indiv_no = 0; indiv_no < spawn_count; indiv_no++) {
            boolean spawned_individual = false;

            for (int attempt = 0; !spawned_individual && attempt < 4; attempt++) {
                var spawnable_pos = DTNWolfSpawnPlacements.getDTNWolfTopNonCollidingPos(DTNEntityTypes.DTNWOLF.get(), level_accessor, check_x, check_z);
                if (DTNWolfSpawnPlacements.spawnPlacementTypeCheck(level_accessor, spawnable_pos, config)) {
                    spawned_individual = doSpawnIndividual(level_accessor, config, min_x, min_z, spawnable_pos, spawngroupdata, rand);
                    if (!spawned_individual)
                        continue;
                }

                int check_x0 = check_x;
                int check_z0 = check_z;

                check_x = check_x0 + randTriangle(rand, 4);
                check_z = check_z0 + randTriangle(rand, 4);
                
                //while not in range
                while (check_x < min_x || check_x >= min_x + 16 || check_z < min_z || check_z >= min_z + 16) {
                    check_x = check_x0 + randTriangle(rand, 4);
                    check_z = check_z0 + randTriangle(rand, 4);
                }
            }
        }
    }

    private static boolean doSpawnIndividual(ServerLevelAccessor level_accessor, WolfBiomeConfig config,
        int min_x, int min_z, BlockPos check_pos, Mutable<SpawnGroupData> spawn_group_mut, RandomSource rand) {
        float entity_width = wolfType().getWidth();
        int check_x = check_pos.getX();
        int check_z = check_pos.getZ();
                    
        //Clamp so that Bb always fit inside chunk
        double check_x_fit = Mth.clamp(check_x, min_x + entity_width, min_x + 16.0 - entity_width);
        double check_z_fit = Mth.clamp(check_z, min_z + entity_width, min_z + 16.0 - entity_width);
        check_pos = BlockPos.containing(check_x_fit, (double)check_pos.getY(), check_z_fit);

        var spawn_aabb = wolfType().getSpawnAABB(check_x_fit, (double)check_pos.getY(), check_z_fit);
        boolean no_collision_at_pos = level_accessor.noCollision(spawn_aabb);
        if (!no_collision_at_pos) {
            return false;
        }
        
        boolean spawn_rule_check = DTNWolfSpawnPlacements.DTNWolfSpawnableOn(config, level_accessor, MobSpawnType.CHUNK_GENERATION, check_pos, level_accessor.getRandom());
        if (!spawn_rule_check)
            return false;

        Entity spawned_entity;
        try {
            spawned_entity = wolfType().create(level_accessor.getLevel());
        } catch (Exception exception) {
            //LOGGER.warn("Failed to create mob", (Throwable)exception);
            return false;
        }

        if (spawned_entity == null) {
            return false;
        }

        spawned_entity.moveTo(check_x_fit, (double)check_pos.getY(), check_z_fit, rand.nextFloat() * 360.0F, 0.0F);
        if (spawned_entity instanceof DTNWolf wolf
            && net.neoforged.neoforge.event.EventHooks.checkSpawnPosition(wolf, level_accessor, MobSpawnType.CHUNK_GENERATION)) {
            var spawn_group0 = spawn_group_mut.getValue();
            if (spawn_group0 == null)
                spawn_group0 = wolf.initializeGroupData(level_accessor, config);
            var spawngroupdata = wolf.finalizeSpawn(
                level_accessor, level_accessor.getCurrentDifficultyAt(wolf.blockPosition()), MobSpawnType.CHUNK_GENERATION, spawn_group0
            );
            spawn_group_mut.setValue(spawngroupdata);
            level_accessor.addFreshEntityWithPassengers(wolf);
            return true;
        }
        return false;
    }

    private static int randTriangle(RandomSource rand, int vary_range) {
        return rand.nextInt(vary_range + 1) - rand.nextInt(vary_range + 1); 
    }

    private static EntityType<DTNWolf> wolfType() {
        return DTNEntityTypes.DTNWOLF.get();
    }

}
