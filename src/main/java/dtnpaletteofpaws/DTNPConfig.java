package dtnpaletteofpaws;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class DTNPConfig {
    
    public static ServerConfig SERVER;
    private static ModConfigSpec SERVER_SPEC;

    public static void init(IEventBus mod_event_bus) {
        var server_config_pair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = server_config_pair.getRight();
        SERVER = server_config_pair.getLeft();
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    public static class ServerConfig {
        public ServerConfig(ModConfigSpec.Builder builder) {
        }
    }

}
