package dtnpaletteofpaws.common.entity.ai.nav;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DTNWolfPathNavigation extends GroundPathNavigation {
    private DTNWolf dog;

    public DTNWolfPathNavigation(DTNWolf dog, Level level) {
        super(dog, level);
        this.dog = dog;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isDone()) {
            if (this.path != null && this.path.isDone())
                this.stop();
        }
    }

    @Override
    protected void followThePath() {
        if (invalidateIfNextNodeIsTooHigh()) return;

        var currentPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = 0.45f;

        var nextPos = this.path.getNextNodePos();
        double dx = Math.abs(this.mob.getX() - ((double)nextPos.getX() + 0.5));
        double dy = Math.abs(this.mob.getY() - (double)nextPos.getY());
        double dz = Math.abs(this.mob.getZ() - ((double)nextPos.getZ() + 0.5));

        boolean isCloseEnough = 
            dx <= (double)this.maxDistanceToWaypoint 
            && dy < 1.0D
            && dz <= (double)this.maxDistanceToWaypoint;
        // boolean canCutCorner = 
        //     this.canCutCorner(this.path.getNextNode().type) 
        //     && this.shouldTargetNextNodeInDirection(currentPos);

        if (isCloseEnough) {
            this.path.advance();
        }
  
        this.doStuckDetection(currentPos);
    }

    protected boolean invalidateIfNextNodeIsTooHigh() {
        var path = this.path;
        if (path == null) return true;
        var nextPos = path.getNextNodePos();
        var dy = this.dog.getY() - (double)nextPos.getY();
                
        if (dy < -1.75) {
            this.stop();
            return true;
        }

        var nextNode = path.getNextNode();
        boolean is_first_fence_node = 
            nextNode.type == BlockPathTypes.FENCE
            && path.getNextNodeIndex() == 0;
        if (!is_first_fence_node && dog.getPathfindingMalus(nextNode.type) < 0) {
            this.stop();
            return true;
        }

        return false;
    }

    // private boolean shouldTargetNextNodeInDirection(Vec3 current_pos) {
    //     var path = this.path;
    //     if (path == null) return false;
    //     if (path.getNextNodeIndex() + 1 >= path.getNodeCount()) {
    //        return false;
    //     }
        
    //     var next_pos = Vec3.atBottomCenterOf(path.getNextNodePos());
    //     if (!current_pos.closerThan(next_pos, 2.0D)) {
    //         return false;
    //     }

    //     Vec3 next2th_node = Vec3.atBottomCenterOf(path.getNodePos(path.getNextNodeIndex() + 1));
    //     Vec3 v_next_next2th = next2th_node.subtract(next_pos);
    //     Vec3 v_next_current = current_pos.subtract(next_pos);
    //     //small alpha
    //     if (v_next_next2th.dot(v_next_current) <= 0.0D) return false;

    //     Vec3 v_current_next2th = next2th_node.subtract(current_pos);
    //     double v_current_next2th_lSqr = v_current_next2th.lengthSqr();
    //     if (v_current_next2th_lSqr < 1) return true;
    //     Vec3 v_add = v_current_next2th.normalize();
    //     var check_b0 = BlockPos.containing(current_pos.add(v_add));
    //     var type = WalkNodeEvaluator
    //         .getPathTypetatic(level, check_b0.mutable());
    //     return type == PathType.WALKABLE;
    // }

    @Override
    protected boolean canUpdatePath() {
        return (super.canUpdatePath());
    }
    
    @Override
    protected boolean hasValidPathType(BlockPathTypes type) {
        if (dog.fireImmune()) {
            if (type == BlockPathTypes.LAVA)
                return true;
            if (type == BlockPathTypes.DAMAGE_FIRE)
                return true;
            if (type == BlockPathTypes.DANGER_FIRE)
                return true;
        }
        return super.hasValidPathType(type);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        if (dog.fireImmune()) {
            if (this.level.getFluidState(pos).is(FluidTags.LAVA))
                return true;
        }
        return super.isStableDestination(pos);
    }

    @Override
    protected PathFinder createPathFinder(int p_26453_) {
        this.nodeEvaluator = new WalkNodeEvaluator() {
            @Override
            protected double getFloorLevel(BlockPos pos) {
                if (dog.fireImmune()) {
                    if (dog.level().getFluidState(pos).is(FluidTags.LAVA)) {
                        return pos.getY();
                    }
                }
                return super.getFloorLevel(pos);
            }
        };
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, p_26453_);
    }

    @Override
    @Nullable
    protected Path createPath(@Nonnull Set<BlockPos> pos, int p_148224_, boolean p_148225_, int p_148226_,
            float p_148227_) {
        dogThrowIfLockAndDebug();  
        return super.createPath(pos, p_148224_, p_148225_, p_148226_, p_148227_);
    }

    //Debug only
    private void dogThrowIfLockAndDebug() {
        // if (locked) {
        //     ChopinLogger.lwn(dog, "Someone trying to create path from outside!");
        //     throw new IllegalStateException(dog.getName().getString() + ": Someone trying to create path from outside!");
        // }
    }
}
