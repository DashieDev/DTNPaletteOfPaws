package dtnpaletteofpaws.common.fabric_helper;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import dtnpaletteofpaws.common.forge_imitate.ServerAboutToStartEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FabricEventCallbackHandler {
    
    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            EventCallbacksRegistry.postEvent(new ServerAboutToStartEvent(server));         
        });
    }

}
