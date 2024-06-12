package dtnpaletteofpaws.common.network;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.network.data.WolfShakingData;
import dtnpaletteofpaws.common.network.data.WolfShakingData.State;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

public class WolfShakingPacket implements IPacket<WolfShakingData> {
    @Override
    public void encode(WolfShakingData data, FriendlyByteBuf buf) {
        buf.writeInt(data.dogId);
        buf.writeByte((byte) data.state.getId());
    }

    @Override
    public WolfShakingData decode(FriendlyByteBuf buf) {
        int dogId = buf.readInt();
        var state = WolfShakingData.State.fromId((int) buf.readByte());
        return new WolfShakingData(dogId, state);
    }

    @Override
    public void handle(WolfShakingData data, Supplier<Context> ctx) {
        
        ctx.get().enqueueWork(() -> {

            if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                Minecraft mc = Minecraft.getInstance();
                Entity e = mc.level.getEntity(data.dogId);
                if (e instanceof DTNWolf wolf) {
                    wolf.handleDogShakingUpdate(data.state);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }

    public static void sendDogShakingPacket(DTNWolf dog, State state) {
        PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> dog), new WolfShakingData(dog.getId(), state));
    }
}
