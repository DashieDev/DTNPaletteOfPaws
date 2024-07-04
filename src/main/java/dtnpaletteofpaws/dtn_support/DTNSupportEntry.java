package dtnpaletteofpaws.dtn_support;

import dtnpaletteofpaws.common.lib.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;

public class DTNSupportEntry {

    public static void start(IEventBus mod_event_bus, IEventBus forge_event_bus) {
        if (!isDTNLoaded())
            return;
        DTNSupportSetup.start(mod_event_bus, forge_event_bus);
    }

    public static void startCommonSetup() {
        if (!isDTNLoaded())
            return;
        DTNSupportSetup.startCommonSetup();
    }

    private static boolean isDTNLoaded() {
        return ModList.get().isLoaded(Constants.DTN_MOD_ID);
    }

}
