package dtnpaletteofpaws.common.entity.ai;

import javax.annotation.Nullable;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class DTNWolfRandomStrollGoal extends RandomStrollGoal {

    private final DTNWolf wolf;

    public DTNWolfRandomStrollGoal(DTNWolf wolf) {
        super(wolf, 1);
        this.wolf = wolf;
    }

    @Override
    @Nullable
    protected Vec3 getPosition() {
        if (this.wolf.getVariant().swimUnderwater() && this.wolf.isInWaterOrBubble()) {
            if (this.wolf.isTame())
                return BehaviorUtils.getRandomSwimmablePos(this.wolf, 10, 7);
            return BehaviorUtils.getRandomSwimmablePos(this.wolf, 10, 3);
        }
        return getWaterAvoidingPos();
    }

    private Vec3 getWaterAvoidingPos() {
        if (this.wolf.isInWater()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3 == null ? super.getPosition() : vec3;
        } else {
            return this.wolf.getRandom().nextFloat() >= WaterAvoidingRandomStrollGoal.PROBABILITY ? LandRandomPos.getPos(this.wolf, 10, 7) : super.getPosition();
        }
    }
    
}
