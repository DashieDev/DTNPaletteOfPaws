package dtnpaletteofpaws;

public class DTNPAndDTNPostInitEntry {
    
    private static boolean dtnFinished = false;
    private static boolean dtnpFinished = false;

    public static void markFinish(boolean is_dtnp) {
        if (is_dtnp)
            dtnpFinished = true;
        else
            dtnFinished = true;
        
        if (dtnFinished && dtnpFinished) {
            afterDTNAndDTNP();
            dtnFinished = false; dtnpFinished = false;
        }
    }

    public static void afterDTNAndDTNP() {
        DTNPaletteOfPawsEntry.fireAttributeEvent();
    }
    

}
