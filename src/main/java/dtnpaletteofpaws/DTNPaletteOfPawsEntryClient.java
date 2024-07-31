package dtnpaletteofpaws;

import doggytalents.forge_imitate.event.client.EntityRenderersEvent;
import dtnpaletteofpaws.common.forge_imitate.ClientEventHandlerRegisterer;
import dtnpaletteofpaws.common.forge_imitate.ModEventCallbacksRegistry;
import dtnpaletteofpaws.common.network.DTNNetworkHandlerClient;
import net.fabricmc.api.ClientModInitializer;

public class DTNPaletteOfPawsEntryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventHandlerRegisterer.init();
        fireModelLayersRegistration();
        DTNNetworkHandlerClient.initClient();
    }

    private static void fireModelLayersRegistration() {
        ModEventCallbacksRegistry.postEvent(new EntityRenderersEvent.RegisterLayerDefinitions());
        ModEventCallbacksRegistry.postEvent(new EntityRenderersEvent.RegisterRenderers());
    }
    
}
