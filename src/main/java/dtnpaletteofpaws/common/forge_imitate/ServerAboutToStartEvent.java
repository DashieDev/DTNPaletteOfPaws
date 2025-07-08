package dtnpaletteofpaws.common.forge_imitate;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.server.MinecraftServer;

public class ServerAboutToStartEvent extends Event {
    
    private MinecraftServer server;

    public ServerAboutToStartEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

}
