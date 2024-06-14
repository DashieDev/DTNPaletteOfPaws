package dtnpaletteofpaws;

import java.util.function.Function;
import java.util.function.Supplier;

import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DTNEntityTypes {
    
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_TYPES, Constants.MOD_ID);
    
    public static final Supplier<EntityType<DTNWolf>> DTNWOLF = register("wolf", DTNWolf::new, MobCategory.CREATURE, (b) -> b
        .sized(0.6F, 0.85F)
        .setUpdateInterval(3)
        .setTrackingRange(16)
        .setShouldReceiveVelocityUpdates(true));

    private static <E extends Entity, T extends EntityType<E>> RegistryObject<EntityType<E>> register(final String name, final EntityType.EntityFactory<E> sup, final MobCategory classification, final Function<EntityType.Builder<E>, EntityType.Builder<E>> builder) {
        return register(name, () -> builder.apply(EntityType.Builder.of(sup, classification)).build(Util.getResource(name).toString()));
    }

    private static <E extends Entity, T extends EntityType<E>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ENTITIES.register(name, sup);
    }

    public static void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(DTNWOLF.get(), DTNWolf.createAttributes().build());
    }


}
