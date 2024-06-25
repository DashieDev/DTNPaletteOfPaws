package dtnpaletteofpaws.common.lib;

import dtnpaletteofpaws.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class Constants {

    public static final String MOD_ID = "dtnpaletteofpaws";
    public static final String MOD_NAME = "DTN's Palette of Paws";
    public static final String DTN_MOD_ID = "doggytalents";

    // Network
    public static final ResourceLocation CHANNEL_NAME = Util.getResource("channel");
    public static final String PROTOCOL_VERSION = Integer.toString(3);

    public static class EntityState {

        public static final byte DEATH = 3;
        public static final byte WOLF_SMOKE = 6;
        public static final byte WOLF_HEARTS = 7;
        public static final byte GUARDIAN_SOUND = 21;
        public static final byte TOTEM_OF_UNDYING = 35;
        public static final byte SLIDING_DOWN_HONEY = 53;
    }
}
