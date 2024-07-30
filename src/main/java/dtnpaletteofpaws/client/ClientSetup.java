package dtnpaletteofpaws.client;

import doggytalents.forge_imitate.event.client.EntityRenderersEvent;
import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.client.entity.renderer.DTNWolfRenderer;
import dtnpaletteofpaws.common.lib.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ClientSetup {
    
    public static final ModelLayerLocation DTNWOLF = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dtnwolf"), "main");

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DTNEntityTypes.DTNWOLF.get(), DTNWolfRenderer::new);
    }

    public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DTNWOLF, DTNWolfModel::createBodyLayer);
    }

}
