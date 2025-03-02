package dtnpaletteofpaws;

import dtnpaletteofpaws.common.lib.Constants;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class DTNPConfig {
    
    public static ServerConfig SERVER;
    private static ModConfigSpec SERVER_SPEC;

    public static void init() {
        var server_config_pair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = server_config_pair.getRight();
        SERVER = server_config_pair.getLeft();
        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.SERVER, SERVER_SPEC);
    }

    public static class ServerConfig {

        public ModConfigSpec.BooleanValue DTNP_SPAWN_TOO_COMMON_FIX;

        public ServerConfig(ModConfigSpec.Builder builder) {
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
