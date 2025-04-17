package dtnpaletteofpaws.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;

import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public abstract class DTNWolfRenderLayer_21_3 extends RenderLayer<DTNWolfRenderState_21_3, DTNWolfModel> {

    public DTNWolfRenderLayer_21_3(RenderLayerParent<DTNWolfRenderState_21_3, DTNWolfModel> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int light, DTNWolfRenderState_21_3 render_state,
            float yrot, float xrot) {
        var dog = render_state.wolf;
        var walk_anim_time = render_state.walkAnimationPos;
        var walk_anim_speed = render_state.walkAnimationSpeed;
        var pticks = render_state.partialTick;
        var ticksp = render_state.ageInTicks;
        render(stack, buffer, light, dog, walk_anim_time, walk_anim_speed, pticks, ticksp, yrot, xrot);
    }

    public abstract void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch);
    

}
