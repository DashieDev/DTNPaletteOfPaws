package dtnpaletteofpaws.common.serializer;

import dtnpaletteofpaws.PawsRegistries;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class WolfVariantSerializer implements EntityDataSerializer<WolfVariant> {

    @Override
    public void write(FriendlyByteBuf buf, WolfVariant value) {
        buf.writeRegistryIdUnsafe(PawsRegistries.DTN_WOLF_VARIANT.get(), value);
    }

    @Override
    public WolfVariant read(FriendlyByteBuf buf) {
        var ret = buf.readRegistryIdUnsafe(PawsRegistries.DTN_WOLF_VARIANT.get());
        return ret;
    }

    @Override
    public WolfVariant copy(WolfVariant value) {
        return value;
    }

}
