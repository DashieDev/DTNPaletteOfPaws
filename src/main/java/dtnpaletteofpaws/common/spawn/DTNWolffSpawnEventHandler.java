package dtnpaletteofpaws.common.spawn;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraftforge.event.entity.living.MobSpawnEvent.FinalizeSpawn;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DTNWolffSpawnEventHandler {
    
    @SubscribeEvent
    public void onFinalizeWolfSpawn(FinalizeSpawn event) {
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
