package dtnpaletteofpaws.common.fabric_helper.pack_handler;

import org.apache.commons.lang3.NotImplementedException;

import dtnpaletteofpaws.common.event.PackHandler;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public class FabricPackHandler {

    public static final String FABRIC_BUILTIN_PREFIX = "builtin/";

    public static void init() {
        var mod_container = FabricLoader.getInstance()
            .getModContainer(Constants.MOD_ID).orElseThrow();

        registerPack(
            mod_container,
            PackType.SERVER_DATA, 
            "dtnp_wolf_spawn_rate_inc",
            Component.literal("DTNP Wolf Spawn Increase")
        );

        final boolean add_vanilla_spawn_pack = false; //Enable on 1.20 and under
        if (add_vanilla_spawn_pack) {
            registerPack(
                mod_container,
                PackType.SERVER_DATA,
                "dtnp_vanilla_variants_spawn", 
                Component.literal("DTNP Armored Paws Spawn")
            );
        }
    }

    private static void registerPack(ModContainer mod_container, PackType type, String id, Component displayMsg) {
        if (type != PackType.SERVER_DATA)
            throw new NotImplementedException("Need to be impl-ed when PackHandler accounts for Resources Pack.");
        ResourceManagerHelperImpl.registerBuiltinResourcePack(
            Util.getResource(FABRIC_BUILTIN_PREFIX + id),
            getDatapackLocation(id), 
            mod_container,
            displayMsg,
            ResourcePackActivationType.NORMAL
        );
    }

    private static String getDatapackLocation(String id) {
        return PackHandler.getBuiltinPackLocationNamespace(id);
    }

    public static boolean isBuiltinPack(String name) {
        return name.startsWith(Util.getResource(FABRIC_BUILTIN_PREFIX).toString());
    }

}
