package dtnpaletteofpaws.common.entity.ai.nav;

import javax.annotation.Nullable;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DTNWolfSwimNodeEvaluator extends SwimNodeEvaluator {

    private boolean checkLand = false;
    private DTNWolf dog;

    public DTNWolfSwimNodeEvaluator(DTNWolf dog) {
        super(false);
        this.dog = dog;
    }

    @Override
    public int getNeighbors(Node[] buffer, Node currentNode) {
        this.checkLand = (currentNode.type == BlockPathTypes.WATER);
        int ret = super.getNeighbors(buffer, currentNode);
        this.checkLand = false;
        return ret;
    }

    @Override
    @Nullable
    protected Node findAcceptedNode(int p_263032_, int p_263066_, int p_263105_) {
        Node node = null;
        var PathType = this.getCachedBlockType(p_263032_, p_263066_, p_263105_);
        if (PathType == PathType.WATER) {
            float f = this.mob.getPathfindingMalus(PathType);
            if (f >= 0.0F) {
                node = this.getNode(p_263032_, p_263066_, p_263105_);
                node.type = PathType;
                node.costMalus = Math.max(node.costMalus, f);
                if (this.mob.level().getFluidState(new BlockPos(p_263032_, p_263066_, p_263105_)).isEmpty()) {
                    node.costMalus += 8.0F;
                }
            }
        } else if (checkLand && PathType == PathType.WALKABLE) {
            float f = this.mob.getPathfindingMalus(PathType);
            if (f >= 0.0F) {
                node = this.getNode(p_263032_, p_263066_ + 1, p_263105_);
                node.type = PathType;
                node.costMalus = Math.max(node.costMalus, f);
            }
        }

        return node;
    }

    @Override
    public Node getStart() {
        var ret = super.getStart();
        if (this.dog.isInWater())
            ret.type = BlockPathTypes.WATER;
        return ret;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z, Mob dog) {
        /*
         * TODO Absoluteness of dog bbW.
         * Currently this implementation relies on the fact that currently a dog's bbWidth never
         * surpass one block. The same also applies to DogPathNavigation maxDistance to advance.
         */
        
        var checking_pos = new BlockPos.MutableBlockPos();
        for (int i = y; i < y + this.entityHeight; ++i) {
            checking_pos.set(x, i, z);
            var state = dog.level().getBlockState(checking_pos);
            var fluid = state.getFluidState();

            boolean checkedWalkable = 
                checkLand && i == y
                && checkLand(checking_pos, state, dog.level());
            if (checkedWalkable)
                return BlockPathTypes.WALKABLE;

            if (!fluid.is(FluidTags.WATER))
                return BlockPathTypes.BLOCKED;
        }

        return BlockPathTypes.WATER;

        // for(int i = x; i < x + this.entityWidth; ++i) {
        //    for(int j = y; j < y + this.entityHeight; ++j) {
        //       for(int k = z; k < z + this.entityDepth; ++k) {
        //          var blockstate = level.getBlockState(checking_pos.set(i, j, k));
        //          var fluidstate = blockstate.getFluidState();
        //          if (checkLand && !blockstate.isPathfindable(level, checking_pos, PathComputationType.LAND)) {
        //             if (level.getBlockState(checking_pos.above()).isAir()) {
        //                 var walkType = WalkNodeEvaluator.getPathTypetatic(level, checking_pos.above().mutable());
        //                 if (walkType == PathType.WALKABLE || walkType == PathType.WATER_BORDER) {
        //                     return PathType.WALKABLE;
        //                 }
        //             }
        //          }
  
        //          if (!fluidstate.is(FluidTags.WATER)) {
        //             return PathType.BLOCKED;
        //          }
        //       }
        //    }
        // }
  
        // BlockState blockstate1 = level.getBlockState(checking_pos);
        // return blockstate1.isPathfindable(level, checking_pos, PathComputationType.WATER) ? PathType.WATER : PathType.BLOCKED;
    }

    private boolean checkLand(BlockPos currentPos, BlockState currenState, BlockGetter level) {
        if (currenState.isPathfindable(level, currentPos, PathComputationType.LAND))
            return false;
        var walkType = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level(), currentPos.above().mutable());
        return walkType == BlockPathTypes.WATER_BORDER
            || walkType == BlockPathTypes.WALKABLE;
    }
    
}
