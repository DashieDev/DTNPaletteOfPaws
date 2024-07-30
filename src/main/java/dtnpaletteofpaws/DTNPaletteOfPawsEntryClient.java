package dtnpaletteofpaws;

import doggytalents.forge_imitate.event.client.EntityRenderersEvent;
import dtnpaletteofpaws.common.forge_imitate.ClientEventHandlerRegisterer;
import dtnpaletteofpaws.common.forge_imitate.ForgeNetworkHandlerClient;
import dtnpaletteofpaws.common.forge_imitate.ModEventCallbacksRegistry;
import net.fabricmc.api.ClientModInitializer;

public class DTNPaletteOfPawsEntryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventHandlerRegisterer.init();
        fireModelLayersRegistration();
        ForgeNetworkHandlerClient.initClient();
    }

    private static void fireModelLayersRegistration() {
        ModEventCallbacksRegistry.postEvent(new EntityRenderersEvent.RegisterLayerDefinitions());
        ModEventCallbacksRegistry.postEvent(new EntityRenderersEvent.RegisterRenderers());
    }
    
}
