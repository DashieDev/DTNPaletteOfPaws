package dtnpaletteofpaws.common.forge_imitate;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.InstanceEventCallBack;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent;
import dtnpaletteofpaws.client.ClientSetup;

public class ClientEventHandlerRegisterer {

    private static ClientSetup INST = new ClientSetup();
    
    public static void init() {
        ModEventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<EntityRenderersEvent.RegisterLayerDefinitions>
                (EntityRenderersEvent.RegisterLayerDefinitions.class,
                    ClientSetup::registerLayerDefinitions
                )
        );
        ModEventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<EntityRenderersEvent.RegisterRenderers>
                (EntityRenderersEvent.RegisterRenderers.class,
                    ClientSetup::setupEntityRenderers
                )
        );
    }

}
