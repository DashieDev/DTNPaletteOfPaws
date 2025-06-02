package dtnpaletteofpaws.common.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfigs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder.RegistryBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNWolfBiomeConfigPackProvider {

    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();    
        var prov = event.getLookupProvider();

        var pack_gen = gen.getBuiltinDatapack(event.includeServer(), 
            Constants.MOD_ID, "dtnp_wolf_spawn_rate_inc");
        pack_gen.addProvider(pack_output -> {
            return PackMetadataGenerator.forFeaturePack(pack_output, 
                Component.literal("Increase DTNP Wolf Spawn Rate"));
        });
        RegistryBootstrap<WolfBiomeConfig> pack_contents = 
            ctx -> {
                WolfBiomeConfig.builder(ctx, WolfVariants.MOLTEN)
                    .biome(Biomes.BASALT_DELTAS)
                    .extraSpawnableBlock(Blocks.BASALT)
                    .canSpawnInDark()
                    .spawnChance(0.5f)
                    .buildAndRegister();
            };
        pack_gen.addProvider(wolfBiomeConfigDataProvFactory(prov, pack_contents));

    }

    public static DataProvider.Factory<DatapackBuiltinEntriesProvider> 
        wolfBiomeConfigDataProvFactory(CompletableFuture<HolderLookup.Provider> prov, 
        RegistryBootstrap<WolfBiomeConfig> bootstrap) {
        
        var wolf_biome_set = new RegistrySetBuilder()
            .add(WolfBiomeConfigs.regKey(), bootstrap);
        
        return (pack_output) -> new DatapackBuiltinEntriesProvider(
                pack_output, prov, 
                wolf_biome_set, Set.of(Constants.MOD_ID) 
        );
    }

}
