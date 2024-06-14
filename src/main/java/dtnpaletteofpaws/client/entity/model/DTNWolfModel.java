package dtnpaletteofpaws.client.entity.model;

import com.google.common.collect.ImmutableList;

import dtnpaletteofpaws.common.entity.DTNWolf;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DTNWolfModel extends ColorableAgeableListModel<DTNWolf> {
 
    public ModelPart head;
    public ModelPart realHead;
    public ModelPart body;
    public ModelPart mane;
    public ModelPart legBackRight;
    public ModelPart legBackLeft;
    public ModelPart legFrontRight;
    public ModelPart legFrontLeft;
    public ModelPart tail;
    public ModelPart realTail;

    public DTNWolfModel(ModelPart box) {
        this.head = box.getChild("head");
        this.realHead = this.head.getChild("real_head");
        this.body = box.getChild("body");
        this.mane = box.getChild("upper_body");
        this.legBackRight = box.getChild("right_hind_leg");
        this.legBackLeft = box.getChild("left_hind_leg");
        this.legFrontRight = box.getChild("right_front_leg");
        this.legFrontLeft = box.getChild("left_front_leg");
        this.tail = box.getChild("tail");
        this.realTail = this.tail.getChild("real_tail");
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.legBackLeft, this.legBackRight, this.legFrontLeft, this.legFrontRight, this.tail, this.mane);
    }

    @Override
    public void prepareMobModel(DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        resetAllPose();
        if (dog.isInSittingPose()) {
            this.setUpSitPose(dog, limbSwing, limbSwingAmount, partialTickTime);
        } else {
            this.setUpStandPose(dog, limbSwing, limbSwingAmount, partialTickTime);
        }
        translateBeggingDog(dog, limbSwing, limbSwingAmount, partialTickTime);
        translateShakingDog(dog, limbSwing, limbSwingAmount, partialTickTime);
    }

    public void resetAllPose() {
        this.body.resetPose();
        this.head.resetPose();
        this.legBackLeft.resetPose();
        this.legBackRight.resetPose();
        this.legFrontLeft.resetPose();
        this.legFrontRight.resetPose();
        this.mane.resetPose();
        this.realHead.resetPose();
        this.realTail.resetPose();
        this.tail.resetPose();
    }

    @Override
    public void setupAnim(DTNWolf dog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot += headPitch * ((float)Math.PI / 180F); 
        this.head.yRot += netHeadYaw * ((float)Math.PI / 180F);
        this.tail.xRot = dog.getTailRotation();
        this.tail.yRot = dog.getWagAngle(limbSwing, limbSwingAmount, ageInTicks);
    }

    public void setUpStandPose(DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        animateStandWalking(dog, limbSwing, limbSwingAmount, partialTickTime);
    }

    public void animateStandWalking(DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        float w = Mth.cos(limbSwing * 0.6662F);
        float w1 = Mth.cos(limbSwing * 0.6662F + (float) Math.PI);

        this.legBackRight.xRot += w * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot += w1 * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot += w1 * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot += w * 1.4F * limbSwingAmount;
    }

    public void setUpSitPose(DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.tail.offsetPos(KeyframeAnimations.posVec(0f, -9f, -2f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-27f, 0f, 0f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0.01f, -1f, 0f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-27f, 0f, 0f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0.01f, -1f, 0f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, 0f, 0f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, -6f, -5f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 0f, 0f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, -6f, -5f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-18f, 0f, 0f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -2f, 0f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(-45f, 0f, 0f));
        this.body.offsetPos(KeyframeAnimations.posVec(0f, -4f, -2f));
        this.head.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        this.head.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
    }

    public void translateShakingDog(DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.mane.zRot = dog.getShakeAngle(partialTickTime, -0.08F);
        this.body.zRot = dog.getShakeAngle(partialTickTime, -0.16F);
        this.realTail.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
    }

    public void translateBeggingDog(DTNWolf dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.realHead.zRot = dog.getBegAngle(partialTickTime) + dog.getShakeAngle(partialTickTime, 0.0F);
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
    }

    private static LayerDefinition createBodyLayerInternal(CubeDeformation scale) {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        float var2 = 13.5F;
        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7.0F));
        var real_head = var3.addOrReplaceChild("real_head", CubeListBuilder.create()
                // Head
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, scale)
                // Nose
                .texOffs(0, 10).addBox(-1.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F, scale)
                , PartPose.ZERO);
        real_head.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), 
                PartPose.offset(-2.0F, -3.0F, 0.5F));
        real_head.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), 
                PartPose.offset(2.0F, -3.0F, 0.5F));
    
        var1.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, scale)
        , PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, scale), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(-1F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);
        var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-1.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(1.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-1.5F, 16.0F, -4.0F));
        var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(1.5F, 16.0F, -4.0F));
        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        var5.addOrReplaceChild("real_tail", CubeListBuilder.create()
                .texOffs(9, 18).addBox(-1F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale)
        , PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 32);
    }

}
