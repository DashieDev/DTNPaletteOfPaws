package dtnpaletteofpaws.common.entity.ai;

import java.util.EnumSet;

import javax.annotation.Nullable;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class DTNWolfBegGoal extends Goal {
    
    private final DTNWolf wolf;
    @Nullable
    private Player player;
    private final Level level;
    private final float lookDistance;
    private int lookTime;
    private final TargetingConditions begTargeting;

    public DTNWolfBegGoal(DTNWolf p_25063_, float p_25064_) {
        this.wolf = p_25063_;
        this.level = p_25063_.level();
        this.lookDistance = p_25064_;
        this.begTargeting = TargetingConditions.forNonCombat().range((double)p_25064_);
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.player = this.level.getNearestPlayer(this.wolf, 8);
        return this.player == null ? false : this.playerHoldingInteresting(this.player);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.player.isAlive()) {
            return false;
        } else {
            return this.wolf.distanceToSqr(this.player) > (double)(this.lookDistance * this.lookDistance)
                ? false
                : this.lookTime > 0 && this.playerHoldingInteresting(this.player);
        }
    }

    @Override
    public void start() {
        this.wolf.setBegging(true);
        this.lookTime = this.adjustedTickDelay(40 + this.wolf.getRandom().nextInt(40));
    }

    @Override
    public void stop() {
        this.wolf.setBegging(false);
        this.player = null;
    }

    @Override
    public void tick() {
        this.wolf.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, (float)this.wolf.getMaxHeadXRot());
        this.lookTime--;
    }

    private boolean playerHoldingInteresting(Player player) {
        for (var interactionhand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(interactionhand);
            if (this.wolf.isTame() && stack.is(Items.BONE)) {
                return true;
            }

            if (this.wolf.isFood(stack)) {
                return true;
            }
        }

        return false;
    }
}