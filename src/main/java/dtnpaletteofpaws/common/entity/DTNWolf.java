package dtnpaletteofpaws.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import dtnpaletteofpaws.DTNEntityTypes;
import dtnpaletteofpaws.DTNRegistries;
import dtnpaletteofpaws.DTNSerializers;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.backward_imitate.WolfInteractionResult;
import dtnpaletteofpaws.common.backward_imitate.WolfSound_1_21_5;
import dtnpaletteofpaws.common.entity.ai.*;
import dtnpaletteofpaws.common.entity.ai.nav.*;
import dtnpaletteofpaws.common.lib.Constants.EntityState;
import dtnpaletteofpaws.common.network.WolfShakingPacket;
import dtnpaletteofpaws.common.network.data.WolfShakingData;
import dtnpaletteofpaws.common.spawn.DTNWolfStaticSpawnManager;
import dtnpaletteofpaws.common.spawn.WolfSpawnPlacementType;
import dtnpaletteofpaws.common.util.WolfSpawnUtil;
import dtnpaletteofpaws.common.util.WolfVariantUtil;
import dtnpaletteofpaws.common.variant.WolfVariant;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.fluids.FluidType;

public class DTNWolf extends TamableAnimal {
        
    private static final EntityDataAccessor<Boolean> BEGGING = SynchedEntityData.defineId(DTNWolf.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLLAR_COLOR = SynchedEntityData.defineId(DTNWolf.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<WolfVariant> VARIANT = SynchedEntityData.defineId(DTNWolf.class, DTNSerializers.DTN_WOLF_VARIANT);

    private float percentDecreasePerHealthLost;
    private float maxHealth0;

    private @Nonnull WetSource wetSource = WetSource.NONE;
    private float headRotationCourse;
    private float headRotationCourseOld;
    private boolean isShaking;
    private boolean shakeFire = false;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    private final MoveControl defaultMoveControl;
    private final PathNavigation defaultPathNavigation;

    private final DTNWolfSwimmingManager swimmingManager = new DTNWolfSwimmingManager(this);

    public DTNWolf(EntityType<? extends DTNWolf> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
        this.setTame(false, true);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.defaultMoveControl = this.moveControl;
        this.defaultPathNavigation = this.navigation;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, WolfVariantUtil.getDefault());
        builder.define(BEGGING, false);
        builder.define(COLLAR_COLOR, DyeColor.RED.getId());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DTNWolfFloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new DTNWolfFollwOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new DTNWolfRandomStrollGoal(this));
        this.goalSelector.addGoal(9, new DTNWolfBegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new DTNWolfHurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(7, new DTNWolfSkeletonGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    public boolean isBegging() {
        return entityData.get(BEGGING);
    }

    public void setBegging(boolean val) {
        this.entityData.set(BEGGING, val);
    }

    public WolfVariant getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(WolfVariant variant) {
        this.entityData.set(VARIANT, variant);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(COLLAR_COLOR));
    }

    private void setCollarColor(DyeColor color) {
        this.entityData.set(COLLAR_COLOR, color.getId());
    }

    public ResourceLocation getTexture() {
        WolfVariant wolfvariant = this.getVariant();
        if (this.isTame()) {
            return wolfvariant.texture();
        } else {
            return wolfvariant.wildTexture();
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 20.0F ? WolfSound_1_21_5.getWhineSound(this.level()) : WolfSound_1_21_5.getPantSound(this.level());
        } else {
            return WolfSound_1_21_5.getAmbientSound(this.level());
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.canWolfArmorAbsorb(source) ? SoundEvents.WOLF_ARMOR_DAMAGE : WolfSound_1_21_5.getHurtSound(this.level());
        //return SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WolfSound_1_21_5.getDeathSound(this.level());
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void tick() {
        super.tick();

        updateClassicalAnim();

        if (this.isAlive() && this.getMaxHealth() != this.maxHealth0) {
            this.maxHealth0 = this.getMaxHealth();
            this.percentDecreasePerHealthLost = 1 / this.maxHealth0;
        }

        this.getVariant().tickWolf(this);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        updateDogBeginShake();

        if (this.fireImmune()) {
            this.floatHellhound(this);
        }
        if (!this.level().isClientSide && this.getVariant().swimUnderwater()) {
            this.swimmingManager.tickServer();
        }
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new DTNWolfPathNavigation(this, level);
    }

    @Override
    public float getPathfindingMalus(PathType type) {
        switch (type) {
        default:
            break;
        case WATER:
        case WATER_BORDER:
            if (shouldDogOmitWaterPathWeight())
                return 0;
            break;
        case LAVA:
        case DAMAGE_FIRE:
        case DANGER_FIRE:
            if (fireImmune())
                return 0;
            break;
        // case DOOR_WOOD_CLOSED:
        //     if (this.canDogPassGate())
        //         return 8;
        //     break;
        case DANGER_POWDER_SNOW:
        case POWDER_SNOW:
            return -1;
        }
        return super.getPathfindingMalus(type);
    }

    public PathType inferType(PathType type) {
        if (this.fireImmune()) {
            if (type == PathType.DANGER_FIRE) {
                return PathType.WALKABLE;
            }
            if (type == PathType.LAVA) {
                return PathType.BLOCKED;
            }
        }
        if (
            this.getVariant().swimUnderwater()
        ) {
            if (type == PathType.WATER)
                return PathType.WALKABLE;
        }
        return type;
    }
    
    private boolean shouldDogOmitWaterPathWeight() {
        if (isDogFollowingSomeone())
            return true;
        if (!this.isInWater())
            return false;
        if (this.getVariant().swimUnderwater())
            return true;
        return false;
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        if (this.fireImmune() && state.is(FluidTags.LAVA))
            return true;

        return super.canStandOnFluid(state);
    }

    private void floatHellhound(DTNWolf dog) {
        if (this.level().isClientSide)
            return;
        if (!dog.isInLava()) return;
        var collisioncontext = CollisionContext.of(dog);
        if (collisioncontext.isAbove(LiquidBlock.SHAPE_STABLE, 
            dog.blockPosition(), true) 
            && !dog.level().getFluidState(dog.blockPosition().above()).is(FluidTags.LAVA)) {
            dog.setOnGround(true);
        } else {
            dog.setDeltaMovement(dog.getDeltaMovement().add(0.0D, 0.085D, 0.0D));
        }
    }

    public boolean shouldDogBlockFloat() {
        if (fireImmune() && isInLava())
            return true;
        if (this.isDogSwimming())
            return true;
        return false;
    }
    
    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return !this.getVariant().swimUnderwater();
    }

    private void updateDogBeginShake() {
        if (this.level().isClientSide)
            return;
        if (this.isShaking)
            return;
        if (this.isPathFinding())
            return;
        if (!this.onGround())
            return;
        if (this.wetSource.isNone())
            return;
        if (this.checkDogInWetSourceAndWetTheDog())
            return;
        if (this.wetSource.flame() && this.isOnFire())
            return;
        
        this.startShakingAndBroadcast(this.wetSource.flame());
    }

    private void updateClassicalAnim() {
        if (!this.isAlive())
            return;
            
        updateClassicalBegAnim();

        boolean val = checkDogInWetSourceAndWetTheDog();
        updateClassicalShakeAnim(val);
    }

    private void updateClassicalBegAnim() {
        this.headRotationCourseOld = this.headRotationCourse;
        if (this.isBegging()) {
            this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
        } else {
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
        }
    }

    private boolean checkDogInWetSourceAndWetTheDog() {
        if (this.isInLava()) {
            this.wetSource = WetSource.LAVA;
            return true;
        }
        if (this.isInWater()) {
            this.wetSource = WetSource.WATER;
            return true;
        }
        if (this.isInWaterOrRain()) {
            this.wetSource = WetSource.RAIN;
            return true;
        }
        if (this.isInWater()) {
            this.wetSource = WetSource.BUBBLE_COLUMN;
            return true;
        }
        return false;
    }

    private void updateClassicalShakeAnim(boolean currentlyInWater) {
        if (!this.level().isClientSide) {
            if (this.isShaking)
            if (currentlyInWater) {
                this.finishShaking();
                WolfShakingPacket.sendDogShakingPacket(this, WolfShakingData.State.STOP);
                return;
            }
        }
        if (!this.isShaking)
            return;

        if (this.timeWolfIsShaking == 0.0F) {
            if (!this.shakeFire) this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }

        this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
        this.timeWolfIsShaking += 0.05F;
        if (this.prevTimeWolfIsShaking >= 2.0F) {
            this.wetSource = WetSource.NONE;
            this.finishShaking();
        }

        if (this.timeWolfIsShaking > 0.4F) {
            float f = (float)this.getY();
            int i = (int)(Mth.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);
            Vec3 vec3d = this.getDeltaMovement();

            for (int j = 0; j < i; ++j) {
                float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                if (this.shakeFire) {
                    float r = this.getRandom().nextFloat();
                    var type = ParticleTypes.SMOKE;
                    if (r <= 0.15f) {
                        type = ParticleTypes.LANDING_LAVA;
                    } else if (r <= 0.6f) {
                        type = ParticleTypes.LAVA;
                    }
                    this.level().addParticle(type, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                } else
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
            }
        }

        if (this.timeWolfIsShaking > 0.5) {
            if (this.shakeFire && random.nextInt(6) == 0) this.playSound(SoundEvents.FIRE_EXTINGUISH, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    private void startShaking() {
        if (this.isShaking) return; // don't shake if already shaking
        this.isShaking = true;
        this.shakeFire = false;
        this.timeWolfIsShaking = 0.0F;
        this.prevTimeWolfIsShaking = 0.0F;
    }

    /**
     * Force the dog to shake, only if the dog is not already shaking
     * and broadcast to clients
     * 
     * @param shakeFire
     */
    public void startShakingAndBroadcast(boolean shakeFire) {
        if (this.isShaking) return; //Already shaking
        if (this.level().isClientSide) return;
        if (shakeFire) {
            this.startShakingLava();
            WolfShakingPacket.sendDogShakingPacket(this, WolfShakingData.State.SHAKE_LAVA);
            return;
        }
        this.startShaking();
        WolfShakingPacket.sendDogShakingPacket(this, WolfShakingData.State.SHAKE_WATER);
    }

    private void finishShaking() {
        this.isShaking = false;
        this.shakeFire = false;
        this.timeWolfIsShaking = 0.0F;
        this.prevTimeWolfIsShaking = 0.0F;
    }

    public void startShakingLava() {
        if (this.isShaking) return;
        this.isShaking = true;
        this.shakeFire = true;
        this.timeWolfIsShaking = 0.0F;
        this.prevTimeWolfIsShaking = 0.0F;
    }

    public boolean isShakingLava() {
        return this.isShaking && this.shakeFire;
    }

    public void handleDogShakingUpdate(WolfShakingData.State state) {
        switch (state) {
        case SHAKE_WATER:
            this.startShaking();
            break;
        case SHAKE_LAVA:
            this.startShakingLava();
            break;
        case STOP:
            this.finishShaking();
            break;
        default:
            this.finishShaking();
            break;
        }
    }

    public void resetBeggingRotation() {
        this.headRotationCourse = 0;
        this.headRotationCourseOld = 0;
    }

    public float getTailRotation() {
        if (!this.isTame())
            return ((float) Math.PI / 5f);
        final float full_health_angle = 1.73f;
        float lost_health = this.getMaxHealth() - this.getHealth();
        float lost_health_percent = lost_health * this.percentDecreasePerHealthLost;
        float lost_rad_percent = lost_health_percent * lost_health_percent;
        float lost_rad = Mth.HALF_PI * lost_rad_percent;
        lost_rad = Mth.clamp(lost_rad, 0, Mth.HALF_PI);
        
        return full_health_angle - lost_rad;
    }

    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime) {
        return Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    public boolean isDogSoaked() {
        return this.wetSource.soaked();
    }

    public float getShadingWhileWet(float partialTicks) {
        if (this.getVariant().swimUnderwater())
            return 1;
        return Math.min(0.5F + Mth.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) / 2.0F * 0.5F, 1.0F);
    }

    public float getShakeAngle(float partialTicks, float offset) {
        float f = (Mth.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) + offset) / 1.8F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }

        return Mth.sin(f * (float)Math.PI) * Mth.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    public float getBegAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.headRotationCourseOld, this.headRotationCourse) * 0.15F * (float)Math.PI;
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0);
            this.setHealth(40.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0);
        }
    }

    // @Override
    // public void setTame(boolean tame, boolean update) {
    //     super.setTame(tame, update);
    //     if (update) 
    //     applyTamingSideEffects();
    // }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        
        var stack = player.getItemInHand(hand);

        if (handleWolfHeal(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;
        if (handleChangeDye(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;

        if (handleSetWolfArmor(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;
        if (handleRepairWolfArmor(player, stack).shouldSwing())
            return InteractionResult.SUCCESS; 
        if (handleUnsetWolfArmor(player, stack, hand).shouldSwing())
            return InteractionResult.SUCCESS;
        
        if (handleTameWolf(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;

        var handle_super = super.mobInteract(player, hand);
        if (handle_super.consumesAction())
            return handle_super;

        if (handleWolfSitStand(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;

        return handle_super;
    }

    private WolfInteractionResult handleWolfHeal(Player player, ItemStack stack) {
        if (!this.isTame())
            return WolfInteractionResult.PASS;
        if (!this.isFood(stack))
            return WolfInteractionResult.PASS;
        if (this.getHealth() >= this.getMaxHealth())
            return WolfInteractionResult.PASS;
        
        if (this.level().isClientSide)
            return WolfInteractionResult.SUCCESS;
        var food_props = stack.get(DataComponents.FOOD);
        float nutrition = food_props != null ? (float)food_props.nutrition() : 1.0F;
        this.heal(2.0F * nutrition);
        stack.shrink(1);
        return WolfInteractionResult.SUCCESS;
    }

    private WolfInteractionResult handleChangeDye(Player player, ItemStack stack) {
        var item = stack.getItem();
        if (!this.isTame())
            return WolfInteractionResult.PASS;
        if (!(item instanceof DyeItem dye))
            return WolfInteractionResult.PASS;
        if (!this.isOwnedBy(player))
            return WolfInteractionResult.PASS;
        var color = dye.getDyeColor();
        if (color == this.getCollarColor())
            return WolfInteractionResult.PASS;

        if (this.level().isClientSide)
            return WolfInteractionResult.SUCCESS;
        this.setCollarColor(color);
        stack.shrink(1);
        return WolfInteractionResult.SUCCESS;
    }

    private WolfInteractionResult handleSetWolfArmor(Player player, ItemStack stack) {
        if (!stack.is(Items.WOLF_ARMOR))
            return WolfInteractionResult.PASS;
        if (this.hasWolfArmor())
            return WolfInteractionResult.PASS;
        if (!this.isTame())
            return WolfInteractionResult.PASS;
        if (!this.isOwnedBy(player))
            return WolfInteractionResult.PASS;

        if (this.level().isClientSide)
            return WolfInteractionResult.SUCCESS;
        this.setBodyArmorItem(stack.copyWithCount(1));
        stack.consume(1, player);
        return WolfInteractionResult.SUCCESS;
        //return InteractionResult.PASS;
    }

    private WolfInteractionResult handleRepairWolfArmor(Player player, ItemStack stack) {
        if (stack.getItem() != Items.ARMADILLO_SCUTE)
            return WolfInteractionResult.PASS;
        if (!this.hasWolfArmor())
            return WolfInteractionResult.PASS;
        if (!this.isTame())
            return WolfInteractionResult.PASS;
        if (!this.isOwnedBy(player))
            return WolfInteractionResult.PASS;
        
        var wolf_armor = getBodyArmorItem();
        if (!wolf_armor.isDamaged())
            return WolfInteractionResult.PASS;

        
        if (this.level().isClientSide)
            return WolfInteractionResult.SUCCESS;
        stack.shrink(1);
        this.playSound(SoundEvents.WOLF_ARMOR_REPAIR);
        int repair_val = (int)(stack.getMaxDamage() * 0.125f);
        int new_damage_val = wolf_armor.getDamageValue() - repair_val;
        if (new_damage_val < 0) new_damage_val = 0;
        wolf_armor.setDamageValue(new_damage_val);
        return WolfInteractionResult.SUCCESS;
        // return WolfInteractionResult.PASS;
    }

    private WolfInteractionResult handleUnsetWolfArmor(Player player, ItemStack stack, InteractionHand hand) {
        if (!stack.is(Items.SHEARS))
            return WolfInteractionResult.PASS;
        if (!this.hasWolfArmor())
            return WolfInteractionResult.PASS;
        if (!this.isTame())
            return WolfInteractionResult.PASS;
        if (!this.isOwnedBy(player))
            return WolfInteractionResult.PASS;

        if (this.level().isClientSide)
            return WolfInteractionResult.SUCCESS;
        stack.hurtAndBreak(1, player, getSlotForHand(hand));
        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);

        var wolf_armor0 = this.getBodyArmorItem();
        this.setBodyArmorItem(ItemStack.EMPTY);
        this.spawnAtLocation((ServerLevel)this.level(), wolf_armor0);
        return WolfInteractionResult.SUCCESS;
        //return WolfInteractionResult.PASS;
    }

    private WolfInteractionResult handleTameWolf(Player player, ItemStack stack) {
        if (!stack.is(Items.BONE))
            return WolfInteractionResult.PASS;
        if (this.isTame())
            return WolfInteractionResult.PASS;

        if (this.level().isClientSide)
            return WolfInteractionResult.SUCCESS;
            
        stack.shrink(1);

        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, EntityState.WOLF_HEARTS);
        } else {
            this.level().broadcastEntityEvent(this, EntityState.WOLF_SMOKE);
        }
        
        return WolfInteractionResult.SUCCESS;
    }

    private WolfInteractionResult handleWolfSitStand(Player player, ItemStack stack) {
        if (!this.isOwnedBy(player))
            return WolfInteractionResult.PASS;

        this.setOrderedToSit(!this.isOrderedToSit());
        this.jumping = false;
        this.navigation.stop();
        this.setTarget(null);
        return WolfInteractionResult.SUCCESS;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.WOLF_FOOD);
        //var item = stack.getItem();
        //return item.isEdible() && stack.getFoodProperties(this).isMeat();
    }

    private boolean canWolfArmorAbsorb(DamageSource p_331524_) {
        return this.hasWolfArmor() && !p_331524_.is(DamageTypeTags.BYPASSES_WOLF_ARMOR);
        //return false;
    }

    public boolean hasWolfArmor() {
        return !this.getBodyArmorItem().isEmpty();
        //return false;
    }

    @Override
    protected void hurtArmor(DamageSource source, float amount) {
        this.doHurtEquipment(source, amount, new EquipmentSlot[]{EquipmentSlot.BODY});
    }

    // @Override
    // public boolean doHurtTarget(Entity p_30372_) {
    //     boolean flag = p_30372_.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
    //     if (flag) {
    //         this.doEnchantDamageEffects(this, p_30372_);
    //     }

    //     return flag;
    // }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource p_30386_, float p_30387_) {
        if (this.isInvulnerableTo(level, p_30386_)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }

            return super.hurtServer(level, p_30386_, p_30387_);
        }
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource source, float amount) {
        if (mayWolfArmorAbsorb(source, amount))
            return;
        super.actuallyHurt(level, source, amount);
    }

    private boolean mayWolfArmorAbsorb(DamageSource source, float amount) {
        if (!canWolfArmorAbsorb(source))
            return false;

        var wolf_armor_stack = this.getBodyArmorItem();
        var damage_val0 = wolf_armor_stack.getDamageValue();
        var damage_max0 = wolf_armor_stack.getMaxDamage();
        wolf_armor_stack.hurtAndBreak(Mth.ceil(amount), this, EquipmentSlot.BODY);
        
        this.playWolfArmorCrackSound(useItem, damage_val0, damage_max0);
        return true;
        //return false;
    }

    private void playWolfArmorCrackSound(ItemStack wolf_armor_stack, int damage_val0, int damage_max0) {
        var crackiness0 = Crackiness.WOLF_ARMOR.byDamage(damage_val0, damage_max0);
        var crackiness = Crackiness.WOLF_ARMOR.byDamage(wolf_armor_stack);
        
        if (crackiness == crackiness0)
            return;

        this.playSound(SoundEvents.WOLF_ARMOR_CRACK);
        if (this.level() instanceof ServerLevel sLevel) {
            sLevel.sendParticles(
                new ItemParticleOption(ParticleTypes.ITEM, Items.ARMADILLO_SCUTE.getDefaultInstance()),
                this.getX(), this.getY() + 1.0, this.getZ(),
                20,   0.2, 0.1, 0.2,   0.1
            );
        }
    }

    @Override
    public void die(DamageSource p_30384_) {
        this.wetSource = WetSource.NONE;
        this.finishShaking();
        super.die(p_30384_);
        //ChopinLogger.l("A Wolf died at : " + this.blockPosition());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("CollarColor", (byte)this.getCollarColor().getId());
        compound.putString("variant", WolfVariantUtil.variantToString(this.getVariant()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        var variant_str = compound.getString("variant").orElse("");
        this.setVariant(WolfVariantUtil.variantFromString(variant_str));
        if (compound.getInt("CollarColor").isPresent()) {
            this.setCollarColor(DyeColor.byId(compound.getInt("CollarColor").orElse(-1)));
        }
    }

    @Nullable
    public DTNWolf getBreedOffspring(ServerLevel p_149088_, AgeableMob p_149089_) {
        DTNWolf wolf = DTNEntityTypes.DTNWOLF.get().create(p_149088_, EntitySpawnReason.BREEDING);
        if (wolf != null && p_149089_ instanceof DTNWolf wolf1) {
            if (this.random.nextBoolean()) {
                wolf.setVariant(this.getVariant());
            } else {
                wolf.setVariant(wolf1.getVariant());
            }

            if (this.isTame()) {
                wolf.setOwnerReference(this.getOwnerReference());
                wolf.setTame(true, true);
                if (this.random.nextBoolean()) {
                    wolf.setCollarColor(this.getCollarColor());
                } else {
                    wolf.setCollarColor(wolf1.getCollarColor());
                }
            }
        }

        return wolf;
    }

    @Override
    public boolean canMate(Animal p_30392_) {
        if (p_30392_ == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(p_30392_ instanceof DTNWolf wolf)) {
            return false;
        } else if (!wolf.isTame()) {
            return false;
        } else {
            return wolf.isInSittingPose() ? false : this.isInLove() && wolf.isInLove();
        }
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof Creeper || target instanceof Ghast || target instanceof ArmorStand) {
            return false;
        } else if (target instanceof TamableAnimal otherWolf) {
            return !otherWolf.isTame() || otherWolf.getOwner() != owner;
        } else {
            if (target instanceof Player player && owner instanceof Player player1 && !player1.canHarmPlayer(player)) {
                return false;
            }

            return true;
        }
    }

    @Override
    public boolean fireImmune() {
        return this.getVariant().fireImmune() || super.fireImmune();
    }

    @Override
    public boolean causeFallDamage(double distance, float modifier, DamageSource source) {
        if (this.getVariant().fallImmune())
            return false;

        return super.causeFallDamage(distance, modifier, source);
    }

    public void setNavigation(PathNavigation p) {
        if (this.navigation == p) return;
        this.navigation.stop();
        this.navigation = p;
    }

    //TODO try to replicate the bug and check if moveControl.haveWantedPosition using debug magic
    public void setMoveControl(MoveControl m) {
        breakMoveControl();

        this.moveControl = m;
    }

    public void breakMoveControl() {
        /*
         * Force the MoveControl To Reset :
         * this will set the dog's wanted Position to his current Position
         * which will cause the moveControl to halt movement and reset in the 
         * next tick(). 
         * And then immediately update the moveControl by calling tick() so 
         * that everything is resolved before anything else.
         */
        this.moveControl.setWantedPosition(
            this.getX(), 
            this.getY(), 
            this.getZ(), 1.0
        );
        this.moveControl.tick();

        //Also reset jump just to be sure.
        this.setJumping(false);

        //Also reset accelerations just to be sure.
        this.setSpeed(0.0F);
        this.setXxa(0.0F);
        this.setYya(0.0F);
        this.setZza(0.0F);
    }

    public void resetNavigation() {
        this.setNavigation(defaultPathNavigation);
    };

    public void resetMoveControl() {
        this.setMoveControl(defaultMoveControl);
    };

    private boolean isDogSwimming = false;

    public void setDogSwimming(boolean val) {
        this.isDogSwimming = val;
    }

    public boolean isDogSwimming() {
        return this.isDogSwimming;
    }

    private boolean isDogFollowingSomeone;

    public boolean isDogFollowingSomeone() {
        return isDogFollowingSomeone;    
    }

    public void setDogFollowingSomeone(boolean val) {
        this.isDogFollowingSomeone = val;
    }

    public void setAttributeModifier(Holder<Attribute> attribute, ResourceLocation modifierLoc, BiFunction<DTNWolf, ResourceLocation, AttributeModifier> modifierGenerator) {
        var attributeInst = this.getAttribute(attribute);

        var currentModifier = attributeInst.getModifier(modifierLoc);

        // Remove modifier if it exists
        if (currentModifier != null) {

            // Use UUID version as it is more efficient since
            // getModifier would need to be called again
            attributeInst.removeModifier(modifierLoc);
        }

        var newModifier = modifierGenerator.apply(this, modifierLoc);

        if (newModifier != null) {
            attributeInst.addTransientModifier(newModifier);
        }
    }

    public void removeAttributeModifier(Holder<Attribute> attribute, ResourceLocation modifierLoc) {
        var attrib = this.getAttribute(attribute);
        if (attrib == null) return;
        attrib.removeModifier(modifierLoc);
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public void checkDespawn() {
        this.noActionTime = 0;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroup) {
        
        WolfPackData wolf_spawn_group = null;
        if (spawnGroup instanceof WolfPackData wolf_group) {
            wolf_spawn_group = wolf_group;
        } else {
            wolf_spawn_group = initializeGroupData(levelAccessor, DTNWolfStaticSpawnManager.get().currentSpawnBiome().orElse(null));
        }
        
        WolfVariant variant;
        if (wolf_spawn_group == null) {
            variant = WolfVariantUtil.getDefaultForSpawn(this, levelAccessor);
        } else {
            variant = wolf_spawn_group.getWolfVariant(this.getRandom());
        }

        this.setVariant(variant);
        //ChopinLogger.l("wolf spawned at " + this.blockPosition() + " variant : " + this.getVariant().id());
        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, wolf_spawn_group);
    }   

    public WolfPackData initializeGroupData(ServerLevelAccessor levelAccessor, @Nullable Holder<Biome> biome) {
        if (biome == null)
            biome = levelAccessor.getBiome(this.blockPosition());
        var possible_variant = WolfVariantUtil.getPossibleSpawnVariants(this.getRandom(), levelAccessor.registryAccess(), biome);
        if (possible_variant.isEmpty())
            return null;
        return new WolfPackData(possible_variant);
    }

    public WolfPackData initializeGroupData(ServerLevelAccessor levelAccessor, WolfBiomeConfig config) {
        var variants = config.variants();
        if (variants.isEmpty())
            variants = Set.of(WolfVariantUtil.getDefault());
        return new WolfPackData(new ArrayList<>(variants));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }
    
    public static boolean checkWolfSpawnableBlockDefault(LevelAccessor level, BlockPos pos, BlockState state_below) {
        return state_below.is(BlockTags.WOLVES_SPAWNABLE_ON);
    }

    public static boolean checkWolfSpawnableLight(LevelAccessor level, BlockPos pos) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, EntitySpawnReason spawn_type) {
        //We don't use super's CheckSpawnRules for now as light check is already being done
        //before this entity is created and there is no need to checkSpawnRules with respect 
        //to a DTNWOlf instance.
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed(this);
    }

    public boolean DTNWolfCheckAdditionalSpawnObstruction(LevelReader levelReader, 
        WolfBiomeConfig config) {
        
        boolean water_obstruction_check = 
            config.placementType() == WolfSpawnPlacementType.WATER
            || !levelReader.containsAnyLiquid(this.getBoundingBox());
        
        return water_obstruction_check;
    }

    public static class WolfPackData extends AgeableMob.AgeableMobGroupData {

        public final List<WolfVariant> variants;

        private WolfPackData(List<WolfVariant> variants) {
            super(false);
            this.variants = variants;
        }

        public WolfVariant getWolfVariant(RandomSource random) {
            if (this.variants.size() == 1) {
                return this.variants.get(0);
            }
        
            int r_index = random.nextInt(this.variants.size());
            return this.variants.get(r_index);
        }

    }

}
