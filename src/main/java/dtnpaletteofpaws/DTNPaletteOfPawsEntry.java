package dtnpaletteofpaws;

import doggytalents.forge_imitate.event.EntityAttributeCreationEvent;
import dtnpaletteofpaws.common.fabric_helper.FabricEventCallbackHandler;
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
        FabricEventCallbackHandler.init();
        EventHandlerRegisterer.init();
        DTNWolfVariantsFabricSpawn.init();
        //fireAttributeEvent();
        DTNNetworkHandler.init();
        DTNPaletteOfPaws.init();
        PacketHandler.init();
        DTNWolfSpawnPlacements.init();
        
        DTNPConfig.init();
        DTNPAndDTNPostInitEntry.markFinish(true);
    }
    
    private void initAllModRegistries() {
        DTNEntityTypes.ENTITIES.initAll();
        WolfVariants.DTN_WOLF_VARIANT.initAll();
        VanillaWolfVariants.VANILLA_WOLF_VARIANT.initAll();
        DTNItems.ITEM.initAll();
        DTNParticles.PARTICLES.initAll();
    }
    
    public static void fireAttributeEvent() {
        ModEventCallbacksRegistry.postEvent(new EntityAttributeCreationEvent());
    }

}
