package dtnpaletteofpaws.common.forge_imitate;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import doggytalents.forge_imitate.event.Event;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.EventCallBack;

public class ModEventCallbacksRegistry {

    private static ArrayList<EventCallBack<?>> CALLBACKS = new ArrayList<>();

    public static synchronized void registerCallback(EventCallBack<?> callback) {
        CALLBACKS.add(callback);
    }

    public static <E extends Event> E postEvent(E event) {
        CALLBACKS.forEach(x -> x.mayInvoke(event));
        return event;
    }


}
