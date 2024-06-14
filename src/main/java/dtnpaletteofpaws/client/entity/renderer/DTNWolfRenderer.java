package dtnpaletteofpaws.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import dtnpaletteofpaws.client.ClientSetup;
import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DTNWolfRenderer extends MobRenderer<DTNWolf, DTNWolfModel> {
    
    public DTNWolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DTNWolfModel(ctx.bakeLayer(ClientSetup.DTNWOLF)), 0.5F);
        //this.addLayer(new DedicatedWolfArmorRenderer(this, ctx));
        this.addLayer(new DTNWolfGlowRenderer(this));
        this.addLayer(new DTNWolfCollarRenderer(this));
    }

    public void render(DTNWolf wolf, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (wolf.isDogSoaked()) {
            float f = wolf.getShadingWhileWet(partialTicks);
            this.model.setColor(f, f, f);
        }

        super.render(wolf, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (wolf.isDogSoaked()) {
            this.model.setColor(1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getTextureLocation(DTNWolf p_116526_) {
        return p_116526_.getTexture();
    }

}