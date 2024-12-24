package dtnpaletteofpaws.common.variant.biome_config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class WolfBiomeConfig {
    
    private final Set<WolfVariant> variants;
    private final HolderSet<Biome> biomes;
    private final Set<Block> extraSpawnableBlocks;
    private final boolean canSpawnInDark;
    private final boolean waterSpawn;

    private WolfBiomeConfig(Set<WolfVariant> variants, HolderSet<Biome> biomes, Set<Block> blocks, boolean canSpawnInDark, boolean waterSpawn) {
        this.variants = variants;
        this.biomes = biomes == null ? HolderSet.direct() : biomes;
        this.extraSpawnableBlocks = blocks == null ? Set.of()
            : Set.copyOf(blocks);
        this.canSpawnInDark = canSpawnInDark;
        this.waterSpawn = waterSpawn;
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

    public boolean waterSpawn() {
        return this.waterSpawn;
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

    public static class Builder {

        private final BootstapContext<WolfBiomeConfig> ctx;
        
        private final ResourceKey<WolfBiomeConfig> id;
        private Set<WolfVariant> variants = Set.of();
        private HolderSet<Biome> biomes = HolderSet.direct(List.of());
        private Set<Block> extraSpawnableBlocks = Set.of();
        private boolean canSpawnInDark = false;
        private boolean waterSpawn = false;

        private Builder(BootstapContext<WolfBiomeConfig> ctx, ResourceKey<WolfBiomeConfig> id) {
            this.ctx = ctx;
            this.id = id;
        }

        public Builder variants(List<WolfVariant> variant_list) {
            this.variants = new HashSet<>(variant_list);
            return this;
        }

        public Builder biome(ResourceKey<Biome> biome) {
            return biomes(List.of(biome));
        }
        
        public Builder biomes(List<ResourceKey<Biome>> biomes) {
            var biome_reg = this.ctx.lookup(Registries.BIOME);
            var biome_holders_list = biomes.stream()
                .map(x -> biome_reg.get(x))
                .filter(x -> x.isPresent())
                .map(x -> x.get())
                .collect(Collectors.toList());
            var biome_holder_set = HolderSet.direct(biome_holders_list);
            return biomes(biome_holder_set);
        }

        public Builder biomes(HolderSet<Biome> biome_set) {
            this.biomes = biome_set;
            return this;
        }

        public Builder extraSpawnableBlocks(List<Block> blocks) {
            this.extraSpawnableBlocks = new HashSet<>(blocks);
            return this;
        }

        public Builder extraSpawnableBlock(Block block) {
            extraSpawnableBlocks(List.of(block));
            return this;
        }

        public Builder canSpawnInDark() {
            this.canSpawnInDark = true;
            return this;
        }

        public Builder waterSpawn() {
            this.waterSpawn = true;
            return this;
        }

        public WolfBiomeConfig build() {
            return new WolfBiomeConfig(this.variants, biomes, this.extraSpawnableBlocks, canSpawnInDark, waterSpawn);
        }

        public void buildAndRegister() {
            ctx.register(id, build());
        }
    }

    public static final Builder builder(BootstapContext<WolfBiomeConfig> ctx, ResourceLocation id) { 
        return new Builder(ctx, ResourceKey.create(WolfBiomeConfigs.regKey(), id)); 
    }

    public static final Builder builder(BootstapContext<WolfBiomeConfig> ctx, Supplier<WolfVariant> variant_sup) {
        var variant = variant_sup.get();
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var wolf_variant_id = variant_reg.getKey(variant);
        if (wolf_variant_id == null)
            throw new IllegalStateException("unregistered wolf variant");
        var res_key = ResourceKey.create(WolfBiomeConfigs.regKey(), wolf_variant_id);
        return new Builder(ctx, res_key).variants(List.of(variant));
    }

    private static WolfBiomeConfig codecDeserializer(Optional<List<WolfVariant>> variants, 
        HolderSet<Biome> biomes, Optional<List<Block>> blocks,
        boolean canSpawnInDark, boolean waterSpawn) {
        
        return new WolfBiomeConfig(
            new HashSet<>(variants.orElse(List.of())), 
            biomes, new HashSet<>(blocks.orElse(List.of())), 
            canSpawnInDark, waterSpawn);
    }

    public static final Codec<WolfBiomeConfig> CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            Util.deferredCodec(() -> DTNRegistries.DTN_WOLF_VARIANT.get().getCodec())
                .listOf()
                .optionalFieldOf("variants").forGetter(WolfBiomeConfig::variantsAsList),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes")
                .forGetter(WolfBiomeConfig::biomes),
            ForgeRegistries.BLOCKS.getCodec().listOf()
                .optionalFieldOf("blocks").forGetter(WolfBiomeConfig::blocksAsList),
            Codec.BOOL.optionalFieldOf("can_spawn_in_dark", false)
                .forGetter(WolfBiomeConfig::canSpawnInDark),
            Codec.BOOL.optionalFieldOf("water_spawn", false)
                .forGetter(WolfBiomeConfig::waterSpawn)
        )
        .apply(builder, WolfBiomeConfig::codecDeserializer)
    );

}
