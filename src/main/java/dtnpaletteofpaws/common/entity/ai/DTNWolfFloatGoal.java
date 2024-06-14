package dtnpaletteofpaws.common.entity.ai;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class DTNWolfFloatGoal extends FloatGoal {

    private DTNWolf dog;


    public DTNWolfFloatGoal(DTNWolf dog) {
        super(dog);
        this.dog = dog;
    }
    
    @Override
    public boolean canUse() {
        if (this.dog.shouldDogBlockFloat())
            return false;

        return super.canUse();
    }

    
}
