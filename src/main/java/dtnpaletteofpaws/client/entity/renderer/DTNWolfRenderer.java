package dtnpaletteofpaws.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import dtnpaletteofpaws.client.ClientSetup;
import dtnpaletteofpaws.client.backward_imitate.DTNWolfRenderState_21_3;
import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

public class DTNWolfRenderer extends MobRenderer<DTNWolf, DTNWolfRenderState_21_3, DTNWolfModel> {
    
    public DTNWolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DTNWolfModel(ctx.bakeLayer(ClientSetup.DTNWOLF)), 0.5F);
        //this.addLayer(new DedicatedWolfArmorRenderer(this, ctx));
        this.addLayer(new DTNWolfGlowRenderer(this));
        this.addLayer(new DTNWolfCollarRenderer(this));
        this.addLayer(new DTNWolfArmorRenderer(this, ctx));
    }

    public void render(DTNWolfRenderState_21_3 wolf_render_state_21_3, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        var wolf = wolf_render_state_21_3.wolf;
        
        if (wolf.isDogSoaked()) {
            //float f = wolf.getShadingWhileWet(partialTicks);
            //this.model.setColor(ARGB.colorFromFloat(1, f, f, f));
        }

        super.render(wolf_render_state_21_3, matrixStackIn, bufferIn, packedLightIn);
        if (wolf.isDogSoaked()) {
            //this.model.setColor(0xffffffff);
        }
    }

    public ResourceLocation getTextureLocation(DTNWolf p_116526_) {
        return p_116526_.getTexture();
    }


    
    //1.21.5+
    @Override
    public void extractRenderState(DTNWolf wolf, DTNWolfRenderState_21_3 render_state, float pticks) {
        super.extractRenderState(wolf, render_state, pticks);
        render_state.wolf = wolf;
    }

    @Override
    public DTNWolfRenderState_21_3 createRenderState() {
        return new DTNWolfRenderState_21_3();
    }

    @Override
    public ResourceLocation getTextureLocation(DTNWolfRenderState_21_3 renderState_21_3) {
        return getTextureLocation(renderState_21_3.wolf);
    }

}