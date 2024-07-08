package dtnpaletteofpaws.common.spawn;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

public class DTNWolffSpawnEventHandler {
    
    @SubscribeEvent
    public void onFinalizeWolfSpawn(FinalizeSpawnEvent event) {
        var target = event.getEntity();
        if (target.getType() != DTNEntityTypes.DTNWOLF.get())
            return;
        if (!(target instanceof DTNWolf wolf))
            return;
        var spawn_data = event.getSpawnData();
        if (spawn_data != null)
            return;
        var level = event.getLevel();
        spawn_data = wolf.initializeGroupData(level);
        event.setSpawnData(spawn_data);
    }

}
