package dtnpaletteofpaws.dtn_support;

import dtnpaletteofpaws.dtn_support.variant.DTNDogVariantMapping;
import dtnpaletteofpaws.dtn_support.variant.DTNSupportDogVariants;
import net.minecraftforge.eventbus.api.IEventBus;

public class DTNSupportSetup {
    
    public static void start(IEventBus mod_event_bus, IEventBus forge_event_bus) {
        DTNSupportDogVariants.DOG_VARIANT.register(mod_event_bus);
        DTNSupportEventHandler.registerSelf(forge_event_bus);
    }

    public static void startCommonSetup() {
        DTNDogVariantMapping.init();
    }

}
