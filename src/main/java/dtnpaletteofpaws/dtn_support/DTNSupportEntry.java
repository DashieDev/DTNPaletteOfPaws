package dtnpaletteofpaws.dtn_support;

import dtnpaletteofpaws.common.lib.Constants;

public class DTNSupportEntry {

    public static void start() {
        if (!isDTNLoaded())
            return;
        DTNSupportSetup.start();
    }

    public static void startCommonSetup() {
        if (!isDTNLoaded())
            return;
        DTNSupportSetup.startCommonSetup();
    }

    private static boolean isDTNLoaded() {
        //return ModList.get().isLoaded(Constants.DTN_MOD_ID);
        return true;
    }

}
