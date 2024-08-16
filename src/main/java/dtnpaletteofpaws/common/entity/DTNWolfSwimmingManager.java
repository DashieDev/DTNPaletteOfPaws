package dtnpaletteofpaws.common.entity;

import java.util.UUID;

import doggytalents.forge_imitate.atrrib.ForgeMod;
import dtnpaletteofpaws.common.entity.ai.nav.DTNWolfSwimMoveControl;
import dtnpaletteofpaws.common.entity.ai.nav.DTNWolfWaterBoundNavigation;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DTNWolfSwimmingManager {
    
    private final DTNWolf dog;

    private static final UUID SWIM_BOOST_ID = UUID.fromString("03850b73-fe12-42bc-984d-45f3911f6d8a");
    private DTNWolfSwimMoveControl moveControl_water;
    private DTNWolfWaterBoundNavigation navigator_water;
    private boolean swimming = false;
    private static final float baseSwimSpeedModifierAdd = 2;
    private float swimSpeedModifierAdd = baseSwimSpeedModifierAdd;

    public DTNWolfSwimmingManager(DTNWolf dog) {
        this.dog = dog;
        moveControl_water = new DTNWolfSwimMoveControl(dog);
        navigator_water = new DTNWolfWaterBoundNavigation(dog, dog.level());
    }

    public void tickServer() {
        if (this.swimming) updateSwimming(dog);
        else updateNotSwimming(dog);
    }

    private void updateSwimming(DTNWolf dog) {
        if (
            (!dog.isInWater() && dog.onGround())
        ) {
            this.swimming = false;
            stopSwimming(dog);
        }
    }

    private void updateNotSwimming(DTNWolf dog) {
        if (
            dog.isInWater()
            && readyToBeginSwimming(dog)
            && !dog.isDogSwimming()
        ) {
            this.swimming = true;
            this.startSwimming(dog);
        }
    }

    private boolean readyToBeginSwimming(DTNWolf dog) {
        return dog.getAirSupply() == dog.getMaxAirSupply();
    }

    private void applySwimAttributes(DTNWolf dog){
        dog.setAttributeModifier(ForgeMod.SWIM_SPEED.get(), SWIM_BOOST_ID, (dd, u) -> 
            new AttributeModifier(u, "DTNP Swim Boost" ,7, Operation.ADDITION)
        );
    }

    private void removeSwimAttributes(DTNWolf dog) {
        dog.removeAttributeModifier(ForgeMod.SWIM_SPEED.get(), SWIM_BOOST_ID);
    }
    
    private void startSwimming(DTNWolf dog) {
        dog.setJumping(false);
        dog.setNavigation(this.navigator_water);
        dog.setMoveControl(this.moveControl_water);
        if (dog.isInSittingPose()) { 
            dog.setInSittingPose(false);
        }
        applySwimAttributes(dog);
        dog.setDogSwimming(true);
    }

    private void stopSwimming(DTNWolf dog) {
        dog.resetMoveControl();
        dog.resetNavigation();
        removeSwimAttributes(dog);
        dog.setDogSwimming(false);
    }

}
