package dtnpaletteofpaws.common.network;

import dtnpaletteofpaws.DTNPaletteOfPaws;
import dtnpaletteofpaws.common.network.data.WolfShakingData;
import net.minecraftforge.network.PacketDistributor;

public class PacketHandler {

    private static int disc = 0;

    public static void init() {
        registerPacket(new WolfShakingPacket(), WolfShakingData.class);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        DTNPaletteOfPaws.HANDLER.send(target, message);
    }

    public static <D> void registerPacket(IPacket<D> packet, Class<D> dataClass) {
        DTNPaletteOfPaws.HANDLER.registerMessage(PacketHandler.disc++, dataClass, packet::encode, packet::decode, packet::handle);
    }
}
