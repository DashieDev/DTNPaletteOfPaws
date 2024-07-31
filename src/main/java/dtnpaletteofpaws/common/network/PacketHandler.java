package dtnpaletteofpaws.common.network;

import dtnpaletteofpaws.DTNPaletteOfPaws;
import dtnpaletteofpaws.common.network.data.FabricWolfVariantSyncData;
import dtnpaletteofpaws.common.network.data.WolfShakingData;

public class PacketHandler {

    private static int disc = 0;

    public static void init() {
        registerPacket(new WolfShakingPacket(), WolfShakingData.class);
        registerPacket(new FabricWolfVariantSyncPacket(), FabricWolfVariantSyncData.class);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        DTNNetworkHandler.send(target, message);
    }

    public static <D> void registerPacket(IPacket<D> packet, Class<D> dataClass) {
        DTNNetworkHandler.registerMessage(PacketHandler.disc++, dataClass, packet::encode, packet::decode, packet::handle);
    }
}
