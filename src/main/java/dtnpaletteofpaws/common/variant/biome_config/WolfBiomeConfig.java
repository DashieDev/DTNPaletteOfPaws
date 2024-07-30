package dtnpaletteofpaws.common.variant.biome_config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class WolfBiomeConfig {
    
    private final Set<WolfVariant> variants;
    private final HolderSet<Biome> biomes;
    private final Set<Block> extraSpawnableBlocks;
    private final boolean canSpawnInDark;

    public WolfBiomeConfig(List<WolfVariant> variants, HolderSet<Biome> biomes, List<Block> blocks, boolean canSpawnInDark) {
        this.variants = variants == null ? Set.of() : Set.copyOf(variants);
        this.biomes = biomes == null ? HolderSet.direct() : biomes;
        this.extraSpawnableBlocks = blocks == null ? Set.of()
            : Set.copyOf(blocks);
        this.canSpawnInDark = canSpawnInDark;
    }

    public WolfBiomeConfig(Optional<List<WolfVariant>> variants, HolderSet<Biome> biomes, Optional<List<Block>> blocks, boolean canSpawnInDark) {
        this(variants.orElse(List.of()), biomes, blocks.orElse(List.of()), canSpawnInDark);
    }

    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    public Set<Block> blocks() {
        return this.extraSpawnableBlocks;
    }

    public Set<WolfVariant> variants() {
        return this.variants;
    }

    public boolean canSpawnInDark() {
        return this.canSpawnInDark;
    }

    public Optional<List<Block>> blocksAsList() {
        if (this.extraSpawnableBlocks.isEmpty())
            return Optional.empty();
        return Optional.of(new ArrayList<>(this.extraSpawnableBlocks));
    }

    public Optional<List<WolfVariant>> variantsAsList() {
        if (this.variants.isEmpty())
            return Optional.empty();
        return Optional.of(new ArrayList<>(this.variants));
    }

    public static final Codec<WolfBiomeConfig> CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            Util.deferredCodec(() -> DTNRegistries.DTN_WOLF_VARIANT.get().byNameCodec())
                .listOf()
                .optionalFieldOf("variants").forGetter(WolfBiomeConfig::variantsAsList),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes")
                .forGetter(WolfBiomeConfig::biomes),
            BuiltInRegistries.BLOCK.byNameCodec().listOf()
                .optionalFieldOf("blocks").forGetter(WolfBiomeConfig::blocksAsList),
            Codec.BOOL.optionalFieldOf("can_spawn_in_dark", false)
                .forGetter(WolfBiomeConfig::canSpawnInDark)
        )
        .apply(builder, WolfBiomeConfig::new)
    );

}
