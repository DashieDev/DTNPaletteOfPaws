package dtnpaletteofpaws;

import doggytalents.forge_imitate.event.EntityAttributeCreationEvent;
import dtnpaletteofpaws.common.forge_imitate.EventHandlerRegisterer;
import dtnpaletteofpaws.common.forge_imitate.ModEventCallbacksRegistry;
import dtnpaletteofpaws.common.network.DTNNetworkHandler;
import dtnpaletteofpaws.common.network.PacketHandler;
import dtnpaletteofpaws.common.spawn.DTNWolfSpawnPlacements;
import dtnpaletteofpaws.common.spawn.DTNWolfVariantsFabricSpawn;
import dtnpaletteofpaws.dtn_support.DTNSupportSetup;
import dtnpaletteofpaws.dtn_support.variant.DTNSupportDogVariants;
import net.fabricmc.api.ModInitializer;

public class DTNPaletteOfPawsEntry implements ModInitializer {

    @Override
    public void onInitialize() {
        DTNRegistries.newRegistry();
        DTNRegistries.newDataRegistry();
        initAllModRegistries();
        EventHandlerRegisterer.init();
        DTNWolfVariantsFabricSpawn.init();
        //fireAttributeEvent();
        DTNNetworkHandler.init();
        DTNPaletteOfPaws.init();
        PacketHandler.init();
        DTNWolfSpawnPlacements.init();
        
        DTNPAndDTNPostInitEntry.markFinish(true);
    }
    
    private void initAllModRegistries() {
        DTNEntityTypes.ENTITIES.initAll();
        WolfVariants.DTN_WOLF_VARIANT.initAll();
        DTNItems.ITEM.initAll();
    }
    
    public static void fireAttributeEvent() {
        ModEventCallbacksRegistry.postEvent(new EntityAttributeCreationEvent());
    }

}
