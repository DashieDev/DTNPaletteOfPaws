package dtnpaletteofpaws.common.entity.ai.nav;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.pathfinder.PathType;

public class DTNWolfSwimMoveControl extends MoveControl {

    private DTNWolf dog;

    public DTNWolfSwimMoveControl(DTNWolf dog) {
        super(dog);
        this.dog = dog;
    }
    
    public void tick() {
        if (
            this.operation == MoveControl.Operation.MOVE_TO 
            && !this.dog.getNavigation().isDone()
            && this.dog.isInWater()
        ) {
            double dx = this.wantedX - this.dog.getX();
            double dy = this.wantedY - this.dog.getY();
            double dz = this.wantedZ - this.dog.getZ();
            double l_sqr = dx * dx + dy * dy + dz * dz;
            if (l_sqr < (double)2.5000003E-7F) {
                this.dog.setZza(0.0F);
                return;
            }
            double l_xz = Math.sqrt(dx * dx + dz * dz);

            float speed = (float)(this.speedModifier * this.dog.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.dog.setSpeed(speed);

            float dy_abs = Mth.abs((float)dy);
            if (dy_abs / l_xz >= 6) {
                this.dog.yya = Mth.sign(dy) * speed;
                this.dog.zza = 0;
                return;
            }

            float wantedYRot = (float)(Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
            this.dog.setYRot(this.rotlerp(this.dog.getYRot(), wantedYRot, (float)this.dog.getMaxHeadYRot()));
            this.dog.yBodyRot = this.dog.getYRot();
            this.dog.yHeadRot = this.dog.getYRot();
            
            if (Math.abs(dy) > (double)1.0E-5F || Math.abs(l_xz) > (double)1.0E-5F) {
                float wantedXRot = -((float)(Mth.atan2(dy, l_xz) * (double)(180F / (float)Math.PI)));
                float maxTurnX = dog.getMaxHeadXRot();
                wantedXRot = Mth.clamp(Mth.wrapDegrees(wantedXRot), -maxTurnX, maxTurnX);
                float approachingXRot = this.rotlerp(this.dog.getXRot(), wantedXRot, 5.0F);
                this.dog.setXRot(approachingXRot);
            }

            float f6 = Mth.cos(this.dog.getXRot() * ((float)Math.PI / 180F));
            float f4 = Mth.sin(this.dog.getXRot() * ((float)Math.PI / 180F));
            this.dog.zza = f6 * speed;
            this.dog.yya = -f4 * speed;

            if (!this.maySwimALittleBitUpToReachLand())
                this.mayCheckAndLeapUp();
        } else {
            this.dog.setSpeed(0.0F);
            this.dog.setXxa(0.0F);
            this.dog.setYya(0.0F);
            this.dog.setZza(0.0F);
        }
     }

    private boolean maySwimALittleBitUpToReachLand() {
        var path = this.dog.getNavigation().getPath();
        if (path == null || path.isDone())
            return false;
        if (!this.dog.isInWater())
            return false;
        int next_node_id = path.getNextNodeIndex();
        if (next_node_id >= path.getNodeCount() || next_node_id < 0)
            return false;
        var nextNode = path.getNextNode();
        boolean reach_land = 
            nextNode.type == PathType.WALKABLE
            && nextNode.y - dog.getY() > 0;
        if (!reach_land)
            return false;
        final float upward_add = 0.05f;
        var current_move = dog.getDeltaMovement();
        dog.setDeltaMovement(current_move.add(0, upward_add, 0));
        return true;
    }

    private void mayCheckAndLeapUp() {
        if (!this.dog.isInWater())
            return;
        double dy = this.getWantedY() - dog.getY();
        double dx = this.getWantedX() - dog.getX();
        double dz = this.getWantedZ() - dog.getZ();
        double l_sqr = dx * dx + dz * dz;
        var b0 = BlockPos.containing(
            this.getWantedX(),
            this.getWantedY(),
            this.getWantedZ()   
        ).below();
        
        boolean dyRequiresJump = 
            (dy > 0.1) && l_sqr < 2;
        if (!dyRequiresJump)
            return;
        var b0_state = this.dog.level().getBlockState(b0);
        var b0_collision = b0_state.getCollisionShape(this.dog.level(), b0);
        boolean collisionRequireJump =
            !b0_collision.isEmpty();
        if (collisionRequireJump) {
            final float upward_add = dy < 0.3 ? 0.05f : 0.1f;
            var current_move = dog.getDeltaMovement();
            dog.setDeltaMovement(current_move.add(0, upward_add, 0));
        }
    }

}
