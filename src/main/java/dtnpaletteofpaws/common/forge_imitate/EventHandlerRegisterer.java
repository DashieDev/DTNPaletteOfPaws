package dtnpaletteofpaws.common.forge_imitate;

import doggytalents.forge_imitate.event.EntityAttributeCreationEvent;
import doggytalents.forge_imitate.event.EntityJoinLevelEvent;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.InstanceEventCallBack;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;
import doggytalents.forge_imitate.event.PlayerInteractEvent;
import doggytalents.forge_imitate.event.ServerStoppedEvent;
import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.event.EventHandler;
import dtnpaletteofpaws.dtn_support.DTNSupportEventHandler;

public class EventHandlerRegisterer {
    
    private static EventHandler handlerIst = new EventHandler();
    private static DTNSupportEventHandler dtnHandlerInst = new DTNSupportEventHandler();

    public static void init() {
        ModEventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<EntityAttributeCreationEvent>(
                EntityAttributeCreationEvent.class,
                DTNEntityTypes::addEntityAttributes
            )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, EntityJoinLevelEvent>
                (handlerIst, EntityJoinLevelEvent.class,
                    (x, y) -> x.onEntitySpawn(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, ServerAboutToStartEvent>
                (handlerIst, ServerAboutToStartEvent.class,
                    (x, y) -> x.onServerAboutToStart(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, ServerStoppedEvent>
                (handlerIst, ServerStoppedEvent.class,
                    (x, y) -> x.onServerStopped(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNSupportEventHandler, PlayerInteractEvent.EntityInteract>
                (dtnHandlerInst, PlayerInteractEvent.EntityInteract.class,
                    (x, y) -> x.onWolfRightClickWithTreat(y)
                )
        );
    }

}
