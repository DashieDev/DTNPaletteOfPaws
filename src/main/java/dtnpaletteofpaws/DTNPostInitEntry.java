package dtnpaletteofpaws;

import doggytalents.api.fabric_helper.entry.PostInitEntry;
import dtnpaletteofpaws.dtn_support.DTNSupportEntry;
import dtnpaletteofpaws.dtn_support.DTNSupportSetup;

public class DTNPostInitEntry implements PostInitEntry{
    
    @Override
    public void afterDTNInit() {
        DTNSupportEntry.start();
        DTNSupportEntry.startCommonSetup();

        DTNPAndDTNPostInitEntry.markFinish(false);
    }

}
