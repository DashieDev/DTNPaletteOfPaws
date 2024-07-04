package dtnpaletteofpaws.client;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.client.entity.renderer.DTNWolfRenderer;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientSetup {
    
    public static final ModelLayerLocation DTNWOLF = new ModelLayerLocation(Util.getResource("dtnwolf"), "main");

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DTNEntityTypes.DTNWOLF.get(), DTNWolfRenderer::new);
    }

    public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DTNWOLF, DTNWolfModel::createBodyLayer);
    }

}
