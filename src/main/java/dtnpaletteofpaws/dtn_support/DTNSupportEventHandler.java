package dtnpaletteofpaws.dtn_support;

import java.util.UUID;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.DogBackFlipAction;
import doggytalents.common.event.EventHandler;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.PlayerInteractEvent;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.dtn_support.variant.DTNDogVariantMapping;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
public class DTNSupportEventHandler {
    
    // public static void registerSelf(IEventBus forge_event_bus) {
    //     forge_event_bus.register(new DTNSupportEventHandler());
    // }

    //@SubscribeEvent
    public void onWolfRightClickWithTreat(final PlayerInteractEvent.EntityInteract event) {
        var level = event.getLevel();
        var stack = event.getItemStack();
        var target = event.getTarget();
        var owner = event.getEntity();

        if (stack.getItem() != DoggyItems.TRAINING_TREAT.get()) 
            return;
        if (!(target instanceof DTNWolf wolf)) return;
        event.setCanceled(true);
        
        if (!checkValidWolf(wolf, owner)) {
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }

        if (!level.isClientSide) {
            checkAndTrainWolf(owner, wolf);
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    public static void checkAndTrainWolf(Player trainer, DTNWolf wolf) {
        var level = trainer.level();
        if (level.isClientSide)
            return;
        
        var stack = trainer.getMainHandItem();
        if (stack.getItem() != DoggyItems.TRAINING_TREAT.get()) 
            return;

        if (!checkValidWolf(wolf, trainer))
            return;

        if (!EventHandler.isWithinTrainWolfLimit(trainer)) {
            level.broadcastEntityEvent(wolf, doggytalents.common.lib.Constants.EntityState.WOLF_SMOKE);
            return;
        }

        if (!trainer.getAbilities().instabuild) {
            stack.shrink(1);
        }

        tameWolfIfNeccessary(wolf, trainer);
        //rotateWolfIfNecceassary(wolf, yHeadRot, yBodyRot);
        trainWolf(wolf, trainer, level);
    }

    private static boolean checkValidWolf(DTNWolf wolf, Player owner) {
        if (!wolf.isAlive()) return false;
        boolean condition1 = !wolf.isTame();
        boolean condition2 = wolf.isTame() && wolf.isOwnedBy(owner);
        
        return condition1 || condition2;
    }

    public static void tameWolfIfNeccessary(DTNWolf wolf, Player owner) {
        //Using training treat to convert a vanilla wolf to DTN wolf
        //also counts as taming that wolf.
        if (wolf.isTame())
            return;
        wolf.tame(owner);
    }

    public static void trainWolf(DTNWolf wolf, Player owner, Level level) {
        Dog dog = DoggyEntityTypes.DOG.get().create(level);
        if (dog == null) {
            throw new IllegalStateException("Creator function for the dog returned \"null\"");
        }
        dog.setTame(true, true);
        dog.setOwnerUUID(owner.getUUID());
        dog.maxHealth();
        dog.setOrderedToSit(false);
        dog.setAge(wolf.getAge());
        dog.moveTo(wolf.getX(), wolf.getY(), wolf.getZ(), wolf.getYRot(), wolf.getXRot());
        dog.setYHeadRot(wolf.yBodyRot);
        dog.setYBodyRot(wolf.yBodyRot);
        dog.setYRot(wolf.yBodyRot);

        var wolf_collar_color = wolf.getCollarColor();
        var color = (wolf_collar_color.getTextureDiffuseColor());
        var dog_collar = DoggyAccessories.DYEABLE_COLLAR.get()
            .create(color);
        if (dog_collar != null)
            dog.addAccessory(dog_collar);
        migrateWolfVariant(wolf, dog);
        migrateWolfArmor(wolf, dog);
            
        if (wolf.hasCustomName()) {
            dog.setDogCustomName(wolf.getCustomName());
        }
        
        var wolf_uuid = wolf.getUUID();
        wolf.discard();

        if (level instanceof ServerLevel sL)
            migrateUUID(wolf_uuid, dog, sL);

        level.addFreshEntity(dog);

        dog.triggerAnimationAction(new DogBackFlipAction(dog));
        dog.getJumpControl().jump();
    }

    private static void migrateUUID(UUID uuid, Dog dog, ServerLevel level) {
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return;
        if (level.getEntity(uuid) != null)
            return;
        dog.setUUID(uuid);
    }

    private static void migrateWolfVariant(DTNWolf wolf, Dog dog) {
        var variant = wolf.getVariant();
        dog.setDogVariant(DTNDogVariantMapping.getDTNWolf(variant));
    }

    private static void migrateWolfArmor(DTNWolf wolf, Dog dog) {
        if (!wolf.hasWolfArmor())
            return;
        var armor_stack = wolf.getBodyArmorItem().copyWithCount(1);
        dog.setWolfArmor(armor_stack);
        return;
    }

}
