package dtnpaletteofpaws.common.event;

import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.spawn.DTNWolfStaticSpawnManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

public class EventHandler {
    
    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        var level = entity.level();
        if (level.isClientSide)
            return;

        if (entity instanceof AbstractSkeleton) {
            AbstractSkeleton skeleton = (AbstractSkeleton) entity;
            skeleton.goalSelector.addGoal(3, new AvoidEntityGoal<>(skeleton, DTNWolf.class, 6.0F, 1.0D, 1.2D)); // Same goal as in AbstractSkeletonEntity
        }
    }

    @SubscribeEvent
    public void onServerAboutToStart(final ServerAboutToStartEvent event) {
        DTNWolfStaticSpawnManager.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopped(final ServerStoppedEvent event) {
        DTNWolfStaticSpawnManager.onServerStopped();
    }

}
