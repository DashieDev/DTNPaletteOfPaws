package dtnpaletteofpaws.common.forge_imitate;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ForgeNetworkHandlerClient {
    
    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(
            ForgeNetworkHandler.CHANNEL_ID, 
            (client, handler, buf, responseSender) -> {
                ForgeNetworkHandler.onToClientPacket(client, buf);
            }
        );
    }

}
