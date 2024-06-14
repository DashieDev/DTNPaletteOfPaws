package dtnpaletteofpaws.common.variant.biome_config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class WolfBiomeConfig {

    private final HolderSet<Biome> biomes;
    private final Set<Block> extraSpawnableBlocks;

    public WolfBiomeConfig(HolderSet<Biome> biomes, List<Block> blocks) {
        this.biomes = biomes == null ? HolderSet.direct() : biomes;
        this.extraSpawnableBlocks = blocks == null ? Set.of()
            : Set.copyOf(blocks);
    }

    public WolfBiomeConfig(HolderSet<Biome> biomes, Optional<List<Block>> blocks) {
        this(biomes, blocks.orElse(List.of()));
    }


    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    public Set<Block> blocks() {
        return this.extraSpawnableBlocks;
    }

    public Optional<List<Block>> blocksAsList() {
        if (this.extraSpawnableBlocks.isEmpty())
            return Optional.empty();
        return Optional.of(new ArrayList<>(this.extraSpawnableBlocks));
    }

    public static final Codec<WolfBiomeConfig> CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes")
                .forGetter(WolfBiomeConfig::biomes),
            ForgeRegistries.BLOCKS.getCodec().listOf()
                .optionalFieldOf("blocks").forGetter(WolfBiomeConfig::blocksAsList)
        )
        .apply(builder, WolfBiomeConfig::new)
    );

}
