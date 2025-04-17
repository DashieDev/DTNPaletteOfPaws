package dtnpaletteofpaws.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dtnpaletteofpaws.client.backward_imitate.DTNWolfRenderLayer_21_3;
import dtnpaletteofpaws.client.backward_imitate.DTNWolfRenderState_21_3;
import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class DTNWolfGlowRenderer extends DTNWolfRenderLayer_21_3 {

    public DTNWolfGlowRenderer(RenderLayerParent<DTNWolfRenderState_21_3, DTNWolfModel> parent) {
        super(parent);
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DTNWolf wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (wolf.isInvisible())
            return;
        
        var glow_layer_optional = wolf.getVariant().glowingOverlay(!wolf.isTame());

        if (!glow_layer_optional.isPresent())
            return;
        
        var glow_layer = glow_layer_optional.get();
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(glow_layer));
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }
    
}
