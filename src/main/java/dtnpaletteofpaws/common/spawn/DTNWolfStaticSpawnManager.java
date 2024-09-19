package dtnpaletteofpaws.common.spawn;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DTNWolfStaticSpawnManager {
    
    private static final DTNWolfStaticSpawnManager instance = new DTNWolfStaticSpawnManager();  
    private static final ThreadLocal<Holder<Biome>> currentSpawnBiome = new ThreadLocal<>();

    public static DTNWolfStaticSpawnManager get() {
        return instance;
    }

    public void onChunkGenerationMobSpawn(ServerLevelAccessor level_accessor, Holder<Biome> biome, ChunkPos chunk_pos, RandomSource random) {
        currentSpawnBiome.set(biome);
    }

    public Optional<Holder<Biome>> currentSpawnBiome() {
        var val = currentSpawnBiome.get();
        return Optional.ofNullable(val);
    }


}
