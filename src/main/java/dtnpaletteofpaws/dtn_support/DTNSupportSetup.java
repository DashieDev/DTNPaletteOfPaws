package dtnpaletteofpaws.dtn_support;

import dtnpaletteofpaws.dtn_support.variant.DTNDogVariantMapping;
import dtnpaletteofpaws.dtn_support.variant.DTNSupportDogVariants;

public class DTNSupportSetup {
    
    public static void start() {
        DTNSupportDogVariants.DOG_VARIANT.initAll();
        //DTNSupportEventHandler.registerSelf(forge_event_bus);
    }

    public static void startCommonSetup() {
        //DTNDogVariantMapping.init();
    }

}
