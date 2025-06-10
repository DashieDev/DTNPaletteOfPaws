package dtnpaletteofpaws.common.variant.biome_config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.common.spawn.WolfSpawnPlacementType;
import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class WolfBiomeConfig {
    
    private final Set<WolfVariant> variants;
    private final HolderSet<Biome> biomes;
    private final Set<Block> extraSpawnableBlocks;
    private final boolean canSpawnInDark;
    private final WolfSpawnPlacementType placementType;
    private final int minCount;
    private final int maxCount;
    private final float spawnChance;

    private final List<WolfVariant> variantsAsList;
    private final List<Block> blocksAsList;

    private WolfBiomeConfig(List<WolfVariant> variants, HolderSet<Biome> biomes, List<Block> blocks, boolean canSpawnInDark, WolfSpawnPlacementType placementType, int minCount, int maxCount, float spawnChance) {
        this.variants = new HashSet<>(variants);
        this.biomes = biomes;
        this.extraSpawnableBlocks = new HashSet<>(blocks);
        this.canSpawnInDark = canSpawnInDark;
        this.placementType = placementType;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.spawnChance = Mth.clamp(spawnChance, 0, 1);

        this.variantsAsList = variants;
        this.blocksAsList = blocks;
    }

    public boolean doSpawn() {
        return this.spawnChance > 0;
    }

    public int minCount() {
        return this.minCount;
    }
    
    public int maxCount() {
        return this.maxCount;
    }

    public float spawnChance() {
        return this.spawnChance;
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

    public WolfSpawnPlacementType placementType() {
        return this.placementType;
    }

    public Optional<List<Block>> blocksAsList() {
        return Optional.of(this.blocksAsList)
            .filter(x -> !x.isEmpty());
    }

    public Optional<List<WolfVariant>> variantsAsList() {
        return Optional.of(this.variantsAsList)
            .filter(x -> !x.isEmpty());
    }

    public static float defaultSpawnChance() {
        return 0;
    }

    public static class Builder {

        private final BootstrapContext<WolfBiomeConfig> ctx;
        
        private final ResourceKey<WolfBiomeConfig> id;
        private List<WolfVariant> variants = List.of();
        private HolderSet<Biome> biomes = HolderSet.empty();
        private List<Block> extraSpawnableBlocks = List.of();
        private boolean canSpawnInDark = false;
        private WolfSpawnPlacementType placementType = WolfSpawnPlacementType.GROUND;
        private int minCount = 1;
        private int maxCount = 1;
        private float spawnChance = 0f;

        private Builder(BootstrapContext<WolfBiomeConfig> ctx, ResourceKey<WolfBiomeConfig> id) {
            this.ctx = ctx;
            this.id = id;
        }

        public Builder variant(WolfVariant... variants) {
            if (variants.length <= 0)
                throw new IllegalArgumentException("variants cannot be empty!");
            this.variants = Arrays.asList(variants);
            return this;
        }

        @SafeVarargs
        public final Builder variant(Supplier<WolfVariant>... variants) {
            return variant(Arrays.stream(variants)
                .map(x -> x.get())
                .toArray(WolfVariant[]::new));
        }
        
        @SafeVarargs
        public final Builder biome(ResourceKey<Biome>... biomes) {
            if (biomes.length <= 0)
                throw new IllegalArgumentException("biomes cannot be empty!");
            var biome_reg = this.ctx.lookup(Registries.BIOME);
            var biome_holders_list = Arrays.stream(biomes)
                .map(x -> biome_reg.get(x))
                .filter(x -> x.isPresent())
                .map(x -> x.get())
                .collect(Collectors.toList());
            var biome_holder_set = HolderSet.direct(biome_holders_list);
            return biome(biome_holder_set);
        }

        public Builder biome(HolderSet<Biome> biome_set) {
            this.biomes = biome_set;
            return this;
        }

        public Builder extraSpawnableBlock(Block... blocks) {
            this.extraSpawnableBlocks = Arrays.asList(blocks);
            return this;
        }

        public Builder canSpawnInDark() {
            this.canSpawnInDark = true;
            return this;
        }

        public Builder placementType(WolfSpawnPlacementType val) {
            this.placementType = val;
            return this;
        }

        public Builder packSize(int minCount, int maxCount) {
            this.minCount = minCount;
            this.maxCount = maxCount;
            return this;
        }

        public Builder spawnChance(float val) {
            this.spawnChance = val;
            return this;
        }

        public WolfBiomeConfig build() {
            if (this.variants.isEmpty())
                throw new IllegalStateException(String.format("Attempting to register a config that has no Wolf Variants: %s", 
                    this.id.location().toString()));
            return new WolfBiomeConfig(this.variants, biomes, this.extraSpawnableBlocks, canSpawnInDark, this.placementType, this.minCount, this.maxCount, this.spawnChance);
        }

        public void buildAndRegister() {
            ctx.register(id, build());
        }
    }

    public static final Builder builder(BootstrapContext<WolfBiomeConfig> ctx, ResourceLocation id) { 
        return new Builder(ctx, ResourceKey.create(WolfBiomeConfigs.regKey(), id)); 
    }

    public static final Builder builder(BootstrapContext<WolfBiomeConfig> ctx, Supplier<WolfVariant> variant_sup) {
        var variant = variant_sup.get();
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var wolf_variant_id = variant_reg.getKey(variant);
        if (wolf_variant_id == null)
            throw new IllegalStateException("unregistered wolf variant");
        var res_key = ResourceKey.create(WolfBiomeConfigs.regKey(), wolf_variant_id);
        return new Builder(ctx, res_key).variant(variant);
    }

    private static WolfBiomeConfig codecDeserializer(Optional<List<WolfVariant>> variants, 
        HolderSet<Biome> biomes, Optional<List<Block>> blocks,
        boolean canSpawnInDark, WolfSpawnPlacementType placementType, int minCount, int maxCount, float spawnChance) {
        
        return new WolfBiomeConfig(
            variants.orElse(List.of()), 
            biomes, blocks.orElse(List.of()), 
            canSpawnInDark, placementType, minCount, maxCount, spawnChance);
    }

    public static final Codec<WolfBiomeConfig> CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            DTNRegistries.DTN_WOLF_VARIANT.get().byNameCodec()
                .listOf()
                .optionalFieldOf("variants").forGetter(WolfBiomeConfig::variantsAsList),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes")
                .forGetter(WolfBiomeConfig::biomes),
            BuiltInRegistries.BLOCK.byNameCodec().listOf()
                .optionalFieldOf("blocks").forGetter(WolfBiomeConfig::blocksAsList),
            Codec.BOOL.optionalFieldOf("can_spawn_in_dark", false)
                .forGetter(WolfBiomeConfig::canSpawnInDark),
            WolfSpawnPlacementType.CODEC
                .optionalFieldOf("placement_type", WolfSpawnPlacementType.GROUND)
                .forGetter(WolfBiomeConfig::placementType),
            Codec.INT.optionalFieldOf("min_count", 1)
                    .forGetter(WolfBiomeConfig::minCount),
            Codec.INT.optionalFieldOf("max_count", 1)
                .forGetter(WolfBiomeConfig::maxCount),
            Codec.FLOAT.optionalFieldOf("spawn_chance", defaultSpawnChance())
                .forGetter(WolfBiomeConfig::spawnChance)
        )
        .apply(builder, WolfBiomeConfig::codecDeserializer)
    );

}
