package dtnpaletteofpaws;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class DTNPConfig {
    
    public static ServerConfig SERVER;
    private static ForgeConfigSpec SERVER_SPEC;

    public static void init(IEventBus mod_event_bus) {
        var server_config_pair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = server_config_pair.getRight();
        SERVER = server_config_pair.getLeft();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    public static class ServerConfig {

        public ForgeConfigSpec.BooleanValue DTNP_SPAWN_TOO_COMMON_FIX;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            DTNP_SPAWN_TOO_COMMON_FIX = builder
                .comment("As of now, some biomes doesn't have much spawn causing some DTNP wolf variants")
                .comment("to be much more common than anticipated. This option makes DTNP spawns")
                .comment("in those biomes have a chance to cancel when attempting to carrying out the spawn.")
                .translation("dtnpaletteofpaws.config.fix_dtnp_spawn_too_common ")
                .define("fix_dtnp_spawn_too_common", true);
        }

        public static<T> T getConfigOrDefault(ConfigValue<T> config, T defaultVal) {
            if (SERVER_SPEC.isLoaded()) {
                    return config.get();
            }
            return defaultVal;
        } 
    }

}
