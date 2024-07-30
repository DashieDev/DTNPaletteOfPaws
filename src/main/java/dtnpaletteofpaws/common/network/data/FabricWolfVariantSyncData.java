package dtnpaletteofpaws.common.network.data;

import dtnpaletteofpaws.common.variant.WolfVariant;

public class FabricWolfVariantSyncData {
    
    public final int wolf_id;
    public final WolfVariant variant;

    public FabricWolfVariantSyncData(int wolf_id, WolfVariant variant) {
        this.wolf_id = wolf_id;
        this.variant = variant;
    }
    
}
