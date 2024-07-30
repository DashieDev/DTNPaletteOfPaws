package dtnpaletteofpaws.common.network;

import java.util.function.Supplier;

import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.DTNSerializers;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.forge_imitate.ForgeNetworkHandler.NetworkEvent.Context;
import dtnpaletteofpaws.common.network.data.FabricWolfVariantSyncData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class FabricWolfVariantSyncPacket implements IPacket<FabricWolfVariantSyncData> {

    @Override
    public void encode(FabricWolfVariantSyncData data, FriendlyByteBuf buf) {
        buf.writeInt(data.wolf_id);
        DTNSerializers.DTN_WOLF_VARIANT.write(buf, data.variant);
    }

    @Override
    public FabricWolfVariantSyncData decode(FriendlyByteBuf buf) {
        int dogId = buf.readInt();
        var variant = DTNSerializers.DTN_WOLF_VARIANT.read(buf);
        return new FabricWolfVariantSyncData(dogId, variant);
    }

    @Override
    public void handle(FabricWolfVariantSyncData data, Supplier<Context> ctx) {
        
        ctx.get().enqueueWork(() -> {

            if (ctx.get().isClientRecipent()) { 
                var mc = Minecraft.getInstance();
                var e = mc.level.getEntity(data.wolf_id);
                if (e instanceof DTNWolf wolf) {
                    wolf.fabricWolfVariantSyncher.clientHandlePacket(data);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
    
}
