package dtnpaletteofpaws.common.data;

import java.util.concurrent.CompletableFuture;

import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig.Output;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNWolfBiomeConfigDatapackProvider extends WolfBiomeConfigDatapackProvider {

    public DTNWolfBiomeConfigDatapackProvider(PackOutput output, CompletableFuture<Provider> prov) {
        super(output, prov, 
            "DTNPSpawnOverride", 
            Component.literal("Make DTNP Wolves spawn more frequenly."));
    }

    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();
        var pack_output = gen.getPackOutput();
        var prov = event.getLookupProvider();
        gen.addProvider(event.includeServer(), 
            new DTNWolfBiomeConfigDatapackProvider(pack_output, prov));
    }

    @Override
    protected void addWolfBiomeConfigsToPack(Output output) {
        WolfBiomeConfig.builder(output, WolfVariants.MOLTEN)
            .biome(Biomes.BASALT_DELTAS)
            .extraSpawnableBlock(Blocks.BASALT)
            .canSpawnInDark()
            .spawnChance(0.5f)
            .buildAndRegister();
    }
}
