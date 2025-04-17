package dtnpaletteofpaws.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import dtnpaletteofpaws.client.ClientSetup;
import dtnpaletteofpaws.client.backward_imitate.DTNWolfRenderLayer_21_3;
import dtnpaletteofpaws.client.entity.model.DTNWolfModel;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.util.Util;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DTNWolfArmorRenderer extends DTNWolfRenderLayer_21_3 {
    private static final Map<Crackiness.Level, ResourceLocation> ARMOR_CRACK_LOCATIONS = Map.of(
        Crackiness.Level.LOW,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_low.png"),
        Crackiness.Level.MEDIUM,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_medium.png"),
        Crackiness.Level.HIGH,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_high.png")
    );

    public DTNWolfArmorRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }
    
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        if (!dog.hasWolfArmor())
            return;

        var wolfArmorOptional = getWolfArmorItem(dog);
        if (!wolfArmorOptional.isPresent())
            return;
        var wolfArmorPair = wolfArmorOptional.get();

        var dogModel = this.getParentModel();

        dogModel.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
        dogModel.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        renderWolfArmorLayerMain(dogModel, poseStack, buffer, packedLight, wolfArmorPair.getRight());
        renderWolfArmorLayerDyed(dogModel, poseStack, buffer, packedLight, wolfArmorPair.getLeft(), wolfArmorPair.getRight());
        renderWolfArmorLayerCracks(dogModel, poseStack, buffer, packedLight, wolfArmorPair.getLeft());
    }

    private Optional<Pair<ItemStack, Item>> getWolfArmorItem(DTNWolf dog) {
        var wolf_armor_stack = dog.getBodyArmorItem();
        if (!(isWolfArmor_1_21_5(wolf_armor_stack)))
            return Optional.empty();
        // if (wolfArmorItem.getBodyType() != AnimalArmorItem.BodyType.CANINE)
        //     return Optional.empty();
        return Optional.of(Pair.of(wolf_armor_stack, wolf_armor_stack.getItem()));
    }

    private void renderWolfArmorLayerMain(DTNWolfModel model, PoseStack poseStack, MultiBufferSource buffer, int light, Item item) {
        var vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(WOLF_ARMOR_MAIN_21_3));
        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }

    private void renderWolfArmorLayerDyed(DTNWolfModel model, PoseStack stack, MultiBufferSource buffer, int light, 
        ItemStack itemStack, Item item) {
        if (item != Items.WOLF_ARMOR)
            return;
        int i = DyedItemColor.getOrDefault(itemStack, 0);
        if (ARGB.alpha(i) == 0)
            return;

        var armor_overlay = WOLF_ARMOR_DYE_21_3;
        if (armor_overlay == null)
            return;
            
        float r = (float)ARGB.red(i) / 255.0F;
        float g = (float)ARGB.green(i) / 255.0F;
        float b = (float)ARGB.blue(i) / 255.0F;
        
        model
            .renderToBuffer(
                stack, buffer.getBuffer(RenderType.entityCutoutNoCull(armor_overlay)), light, 
                OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, r, g, b)
            );
    }

    private void renderWolfArmorLayerCracks(DTNWolfModel model, PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack) {
        var crack_level = Crackiness.WOLF_ARMOR.byDamage(itemStack);
        if (crack_level == Crackiness.Level.NONE)
            return;

        var crack_rl = ARMOR_CRACK_LOCATIONS.get(crack_level);
        var vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(crack_rl));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }



    //1.21.5+
    public static boolean isWolfArmor_1_21_5(ItemStack stack) {
        var slot = Optional.ofNullable(
            stack.get(DataComponents.EQUIPPABLE))
            .map(x -> x.slot()).orElse(null);
            
        if (slot == null)
            return false;
        return slot.getType() == EquipmentSlot.Type.ANIMAL_ARMOR;
    }

    private static final ResourceLocation WOLF_ARMOR_MAIN_21_3 = Util.getVanillaResource("textures/entity/wolf/wolf_armor.png");
    private static final ResourceLocation WOLF_ARMOR_DYE_21_3 = Util.getVanillaResource("textures/entity/wolf/wolf_armor_overlay.png");

    
}