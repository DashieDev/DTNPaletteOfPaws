package dtnpaletteofpaws.common.data;

import java.util.Set;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnModifiers;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfigs;
import net.minecraft.core.RegistrySetBuilder;

public class DTNDataRegistriesProvider {
    
    // public static void start(GatherDataEvent event) {
    //     var wolf_biome_set = new RegistrySetBuilder()
    //         .add(WolfBiomeConfigs.regKey(), WolfBiomeConfigs::bootstrap)
    //         .add(ForgeRegistries.Keys.BIOME_MODIFIERS, DTNWolfSpawnModifiers::bootstrap);

    //     var datagen = event.getGenerator();
    //     datagen.addProvider(event.includeServer(),
    //         new DatapackBuiltinEntriesProvider(
    //             datagen.getPackOutput(), event.getLookupProvider(), 
    //             wolf_biome_set, Set.of(Constants.MOD_ID) 
    //         )
    //     );
    // }

}
