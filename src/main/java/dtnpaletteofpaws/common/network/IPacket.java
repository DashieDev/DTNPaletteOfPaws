package dtnpaletteofpaws.common.network;

import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.forge_imitate.ForgeNetworkHandler.NetworkEvent;

public interface IPacket<D> {

    public void encode(D data, FriendlyByteBuf buf);

    public D decode(FriendlyByteBuf buf);

    public void handle(D data, Supplier<NetworkEvent.Context> ctx);
}
