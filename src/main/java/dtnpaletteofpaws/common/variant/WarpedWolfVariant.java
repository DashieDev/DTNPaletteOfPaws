package dtnpaletteofpaws.common.variant;

import dtnpaletteofpaws.common.lib.Resources;

public class WarpedWolfVariant extends WolfVariant{
    public WarpedWolfVariant(String name) {
        super(
            WolfVariant.props(name)
            .glowingOverlay(
                Resources.getDTNWolfTexture("variants/wolf_warped_overlay"),
                Resources.getDTNWolfTexture("variants/wolf_warped_wild_overlay")
            )
        );
    }
}
