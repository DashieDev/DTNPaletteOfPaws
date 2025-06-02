package dtnpaletteofpaws.common.event;

import dtnpaletteofpaws.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
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

}
