package dtnpaletteofpaws.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class DTNWolfCollarRenderer  extends RenderLayer<DTNWolf, DTNWolfModel> {

    private static final ResourceLocation WOLF_COLLAR_LOCATION = Util.getVanillaResource("textures/entity/wolf/wolf_collar.png");

    public DTNWolfCollarRenderer(RenderLayerParent<DTNWolf, DTNWolfModel> p_117707_) {
        super(p_117707_);
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DTNWolf wolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (wolf.isTame() && !wolf.isInvisible()) {
            int afloat = wolf.getCollarColor().getTextureDiffuseColor();
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(WOLF_COLLAR_LOCATION));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, afloat);
        }
    }
}