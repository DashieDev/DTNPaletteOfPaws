package dtnpaletteofpaws.common.event;

import java.util.Optional;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.lib.Resources;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Position;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class PackHandler {
    
    public static void onAddPackFinder(AddPackFindersEvent event) {
        var pack_location = Util.getResource(
            "data/dtnpaletteofpaws/datapacks/dtnp_wolf_spawn_rate_inc");
        event.addPackFinders(pack_location, PackType.SERVER_DATA, Component.literal("DTNP Wolf Spawn Increase"), 
            PackSource.FEATURE, false, Position.TOP);
    }

    public static Optional<ResourceLocation> onPackLoadIcon(Pack pack) {
        var pack_id = pack.getId();
        if (pack_id != null && pack_id.startsWith("mod/" + Constants.MOD_ID)) {
            return Optional.of(Resources.DTNP_PACK_ICON);
        }
        return Optional.empty();
    }

}
