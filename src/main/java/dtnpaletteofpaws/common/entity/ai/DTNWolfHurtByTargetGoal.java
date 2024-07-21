package dtnpaletteofpaws.common.entity.ai;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class DTNWolfHurtByTargetGoal extends HurtByTargetGoal {

    private final DTNWolf wolf;

    public DTNWolfHurtByTargetGoal(DTNWolf wolf) {
        super(wolf);
        this.wolf = wolf;
    }

    @Override
    public boolean canUse() {
        if (!this.wolf.isTame())
            return false;
        return super.canUse();
    }
    
}
