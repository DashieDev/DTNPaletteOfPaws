package dtnpaletteofpaws;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

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

        public ModConfigSpec.BooleanValue DTNP_STATIC_SPAWN;
        public ModConfigSpec.BooleanValue DTNP_STATIC_UNDERGROUND_SPAWN;

        public ServerConfig(ModConfigSpec.Builder builder) {
            DTNP_STATIC_SPAWN = builder
                .comment("Set this to false to disable DTNP Chunk Generation Spawn.")
                .comment("Notice that when this option is disabled DTNP Wolves will not")
                .comment("spawn upon chunk generation.")
                .translation("dtnpaletteofpaws.config.dtnp_static_spawn")
                .worldRestart()
                .define("dtnp_static_spawn", true);
            DTNP_STATIC_UNDERGROUND_SPAWN = builder
                .comment("Set this to false to prevent DTNP Chunk Generation Spawn")
                .comment("from doing any Underground Spawn (e.g Sculk, Charcoal).")
                .translation("dtnpaletteofpaws.config.dtnp_static_underground_spawn")
                .worldRestart()
                .define("dtnp_static_underground_spawn", true);
        }

        public static<T> T getConfigOrDefault(ConfigValue<T> config, T defaultVal) {
            if (SERVER_SPEC.isLoaded()) {
                    return config.get();
            }
            return defaultVal;
        } 
    }

}
