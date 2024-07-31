package dtnpaletteofpaws.common.serializer;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.common.util.NetworkUtil;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class WolfVariantSerializer extends DogSerializer<WolfVariant> {

    @Override
    public void write(FriendlyByteBuf buf, WolfVariant value) {
        NetworkUtil.writeDogVariantToBuf(buf, value);
    }

    @Override
    public WolfVariant read(FriendlyByteBuf buf) {
        var ret = NetworkUtil.readDogVariantFromBuf(buf);
        return ret;
    }

    @Override
    public WolfVariant copy(WolfVariant value) {
        return value;
    }

}
