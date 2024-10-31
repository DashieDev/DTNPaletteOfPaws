package dtnpaletteofpaws.common.entity.ai;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class DTNWolfSkeletonGoal extends NearestAttackableTargetGoal<AbstractSkeleton> {

    private final DTNWolf wolf;

    public DTNWolfSkeletonGoal(DTNWolf wolf) {
        super(wolf, AbstractSkeleton.class, false);
        this.wolf = wolf;
    }

    @Override
    public boolean canUse() {
        if (!this.wolf.isTame())
            return false;
        return super.canUse();
    }

}


