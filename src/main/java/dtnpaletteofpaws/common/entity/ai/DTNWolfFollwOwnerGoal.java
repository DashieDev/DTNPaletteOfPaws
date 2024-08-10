package dtnpaletteofpaws.common.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

import javax.annotation.Nullable;

import dtnpaletteofpaws.common.entity.DTNWolf;

public class DTNWolfFollwOwnerGoal extends Goal {

    private final DTNWolf dog;
    private final Level world;
    private final double followSpeed;

    // If closer than stopDist stop moving towards owner
    private final float stopDist; 
     // If further than startDist moving towards owner
    private final float startDist;

    private LivingEntity owner;
    private int timeToRecalcPath;
    private int tickTillSearchForTp = 0;
    private float oldWaterCost;

    public DTNWolfFollwOwnerGoal(DTNWolf dogIn, double speedIn, float minDistIn, float maxDistIn) {
        this.dog = dogIn;
        this.world = dogIn.level();
        this.followSpeed = speedIn;
        this.startDist = minDistIn;
        this.stopDist = maxDistIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.dog.getOwner();
        if (owner == null) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (this.dog.isInSittingPose()) {
            return false;
        } else if (this.dog.distanceToSqr(owner) < this.getMinStartDistanceSq()) {
            return false;
        } else {
            this.owner = owner;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dog.getNavigation().isDone()) {
            return false;
        } else if (this.dog.isInSittingPose()) {
            return false;
        } else {
            return this.dog.distanceToSqr(this.owner) > this.stopDist * this.stopDist;
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.dog.setDogFollowingSomeone(true);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.dog.getNavigation().stop();
        this.dog.setDogFollowingSomeone(false);
    }

    @Override
    public void tick() {
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            moveToOwnerOrTeleportIfFarAway(
                dog, owner, this.followSpeed,
                144, 
                false, false, 
                400,
                dog.getMaxFallDistance());
        }
    }

    public float getMinStartDistanceSq() {
        // Guard.Major Mode Goal now override this.
        // if (this.dog.isMode(EnumMode.GUARD)) {
        //     return 4F;
        // }

        return this.startDist * this.startDist;
    }

    public static void moveToOwnerOrTeleportIfFarAway(DTNWolf dog, LivingEntity owner, double speedModifier,
        double distanceToTeleportSqr, boolean continueToMoveWhenTryTp, boolean forceTeleport, 
        double distanceToForceTeleportSqr, int dY) {
        if (owner == null) return;
        var distance = dog.distanceToSqr(owner);
        if (!dog.isLeashed() && !dog.isPassenger()) {
            if (distance >= distanceToTeleportSqr) {
                guessAndTryToTeleportToOwner(dog, owner, 4);
            } else {
                dog.getNavigation().moveTo(owner, speedModifier);
            }
        }
    }

    public static boolean guessAndTryToTeleportToOwner(DTNWolf dog, LivingEntity owner, int radius) {
        var owner_b0 = owner.blockPosition();

        for (int i = 0; i < 10; ++i) {
            int randX = owner_b0.getX() + getRandomNumber(dog, -radius, radius);
            int randY= owner_b0.getY() + getRandomNumber(dog, -1, 1);
            int randZ = owner_b0.getZ() + getRandomNumber(dog, -radius, radius);
            var b0 = new BlockPos(randX, randY, randZ);

            if (wantToTeleportToThePosition(dog, owner, b0)) {
                teleportInternal(dog, b0);
                return true;
            }
        }

        return false;
    }

    private static void teleportInternal(DTNWolf dog, BlockPos target) {
        dog.fallDistance = 0;
        dog.moveTo(target.getX() + 0.5F, target.getY(), target.getZ() + 0.5F, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
        dog.breakMoveControl();
    }

    public static boolean wantToTeleportToThePosition(DTNWolf dog, LivingEntity owner, BlockPos pos) {
        var owner_b0 = owner.blockPosition();
        boolean flag = 
                // Not too close to owner
                !(
                    Mth.abs(owner_b0.getX() - pos.getX()) < 2
                    && Mth.abs(owner_b0.getZ() - pos.getZ()) < 2 
                ) 

                // safe?
                && isTeleportSafeBlock(dog, pos, owner);

                // Can see owner at that pos
                // && hasLineOfSightToOwnerAtPos(dog, pos) 
        return flag;
    }

    public static boolean isTeleportSafeBlock(DTNWolf dog, BlockPos pos, @Nullable LivingEntity owner) {
        var pathnodetype = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level(), pos.mutable());
        boolean alterationWalkable = false;
        var infer_type = dog.inferType(pathnodetype);
        if (infer_type == BlockPathTypes.WALKABLE)
            alterationWalkable = true;
        
        if (dog.fireImmune() && pathnodetype == BlockPathTypes.OPEN
            && dog.level().getFluidState(pos.below()).is(FluidTags.LAVA))
            alterationWalkable = true;
        if (pathnodetype != BlockPathTypes.WALKABLE && !alterationWalkable) {
            return false;
        } else {
            var blockpos = pos.subtract(dog.blockPosition());
            return dog.level().noCollision(dog, dog.getBoundingBox().move(blockpos));
        }
    }

    public static int getRandomNumber(DTNWolf entityIn, int minIn, int maxIn) {
        return entityIn.getRandom().nextInt(maxIn - minIn + 1) + minIn;
    }

}

