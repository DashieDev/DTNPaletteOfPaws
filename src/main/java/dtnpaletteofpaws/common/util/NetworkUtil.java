package dtnpaletteofpaws.common.util;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class NetworkUtil {
    
    public static StreamCodec
        <RegistryFriendlyByteBuf, WolfVariant> 
        DOG_VARIANT_CODEC = ByteBufCodecs.registry(DTNRegistries.Keys.DTN_WOLF_VARIANT);

    public static void writeDogVariantToBuf(FriendlyByteBuf buf, WolfVariant val) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        DOG_VARIANT_CODEC.encode(reg_buf, val);
    }

    public static WolfVariant readDogVariantFromBuf(FriendlyByteBuf buf) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        return DOG_VARIANT_CODEC.decode(reg_buf);
    }

}
