package dtnpaletteofpaws.common.variant;

import dtnpaletteofpaws.common.lib.Resources;

public class CrimsonWolfVariant extends WolfVariant{

    public CrimsonWolfVariant(String name) {
        super(
            WolfVariant.props(name)
            .glowingOverlay(
                Resources.getDTNWolfTexture("variants/wolf_crimson_overlay"),
                Resources.getDTNWolfTexture("variants/wolf_crimson_wild_overlay")
            )
        );
    }
    
}
