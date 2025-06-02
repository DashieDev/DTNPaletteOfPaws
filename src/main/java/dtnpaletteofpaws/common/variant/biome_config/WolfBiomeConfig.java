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
import net.minecraft.core.Registry;
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
    private final boolean waterSpawn;
    private final int minCount;
    private final int maxCount;
    private final float spawnChance;

    private WolfBiomeConfig(Set<WolfVariant> variants, HolderSet<Biome> biomes, Set<Block> blocks, boolean canSpawnInDark, boolean waterSpawn, int minCount, int maxCount, float spawnChance) {
        this.variants = variants;
        this.biomes = biomes == null ? HolderSet.direct() : biomes;
        this.extraSpawnableBlocks = blocks == null ? Set.of()
            : Set.copyOf(blocks);
        this.canSpawnInDark = canSpawnInDark;
        this.waterSpawn = waterSpawn;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.spawnChance = Mth.clamp(spawnChance, 0, 1);
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

    public static float defaultSpawnChance() {
        return 0;
    }

    public static class Builder {

        private final Output output;
        
        private final ResourceKey<WolfBiomeConfig> id;
        private Set<WolfVariant> variants = Set.of();
        private HolderSet<Biome> biomes = HolderSet.empty();
        private Set<Block> extraSpawnableBlocks = Set.of();
        private boolean canSpawnInDark = false;
        private boolean waterSpawn = false;
        private int minCount = 1;
        private int maxCount = 1;
        private float spawnChance = 0f;

        private Builder(Output registries, ResourceKey<WolfBiomeConfig> id) {
            this.output = registries;
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
            var biome_reg = this.output.lookup(Registries.BIOME);
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
            return new WolfBiomeConfig(this.variants, biomes, this.extraSpawnableBlocks, canSpawnInDark, waterSpawn, this.minCount, this.maxCount, this.spawnChance);
        }

        public void buildAndRegister() {
            output.accept(id, build());
        }
    }

    public static interface Output {
        
        public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<S>> regKey);

        public void accept(ResourceKey<WolfBiomeConfig> id, WolfBiomeConfig val); 

    }

    public static final Builder builder(BootstrapContext<WolfBiomeConfig> ctx, ResourceKey<WolfBiomeConfig> id) {
        var wrapped = new WolfBiomeConfig.Output() {

            @Override
            public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<S>> regKey) {
                return ctx.lookup(regKey);
            }

            @Override
            public void accept(ResourceKey<WolfBiomeConfig> id, WolfBiomeConfig val) {
                ctx.register(id, val);
            }
            
        };
        return new Builder(wrapped, id);
    }

    public static final Builder builder(BootstrapContext<WolfBiomeConfig> ctx, ResourceLocation id) { 
        return builder(ctx, ResourceKey.create(WolfBiomeConfigs.regKey(), id)); 
    }

    public static final Builder builder(BootstrapContext<WolfBiomeConfig> ctx, Supplier<WolfVariant> variant_sup) {
        var variant = variant_sup.get();
        var variant_reg = DTNRegistries.DTN_WOLF_VARIANT.get();
        var wolf_variant_id = variant_reg.getKey(variant);
        if (wolf_variant_id == null)
            throw new IllegalStateException("unregistered wolf variant");
        var res_key = ResourceKey.create(WolfBiomeConfigs.regKey(), wolf_variant_id);
        return builder(ctx, res_key).variants(List.of(variant));
    }

    private static WolfBiomeConfig codecDeserializer(Optional<List<WolfVariant>> variants, 
        HolderSet<Biome> biomes, Optional<List<Block>> blocks,
        boolean canSpawnInDark, boolean waterSpawn, int minCount, int maxCount, float spawnChance) {
        
        return new WolfBiomeConfig(
            new HashSet<>(variants.orElse(List.of())), 
            biomes, new HashSet<>(blocks.orElse(List.of())), 
            canSpawnInDark, waterSpawn, minCount, maxCount, spawnChance);
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
            Codec.BOOL.optionalFieldOf("water_spawn", false)
                .forGetter(WolfBiomeConfig::waterSpawn),
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
