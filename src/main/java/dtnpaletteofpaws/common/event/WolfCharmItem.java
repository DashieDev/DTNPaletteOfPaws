package dtnpaletteofpaws.common.event;

import java.util.Objects;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.util.WolfVariantUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class WolfCharmItem extends Item {
    
    public WolfCharmItem() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (level.isClientSide)
            return InteractionResult.SUCCESS;
        if (!(level instanceof ServerLevel sLevel))
            return InteractionResult.SUCCESS;
        
        var player = context.getPlayer();
        var stack = context.getItemInHand();
        var clicked_pos = context.getClickedPos();
        var clicked_face = context.getClickedFace();
        var state = level.getBlockState(clicked_pos);

        if (player == null)
            return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown())
            return InteractionResult.SUCCESS;

        BlockPos spawn_pos;
        if (state.getCollisionShape(level, clicked_pos).isEmpty()) {
            spawn_pos = clicked_pos;
        } else {
            spawn_pos = clicked_pos.relative(clicked_face);
        }

        boolean upward_collision = 
            !Objects.equals(spawn_pos, clicked_pos) && clicked_face == Direction.UP;

        var entity = DTNEntityTypes.DTNWOLF.get().spawn(sLevel, stack, 
            context.getPlayer(), spawn_pos, MobSpawnType.SPAWN_EGG, 
            upward_collision, false);
        
        if (!(entity instanceof DTNWolf wolf))
            return InteractionResult.SUCCESS;

        stack.shrink(1);
        if (player instanceof ServerPlayer sP) {
            sP.getCooldowns().addCooldown(this, 30);
        }
        return InteractionResult.SUCCESS;
    }

}
