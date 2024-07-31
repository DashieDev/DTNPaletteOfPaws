package dtnpaletteofpaws;

import java.util.function.Function;
import java.util.function.Supplier;

import doggytalents.forge_imitate.event.EntityAttributeCreationEvent;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class DTNEntityTypes {
    
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(() -> BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);
    
    public static final Supplier<EntityType<DTNWolf>> DTNWOLF = register("wolf", DTNWolf::new, MobCategory.CREATURE, (b) -> b
        .sized(0.6F, 0.85F)
        //.setUpdateInterval(3)
        .updateInterval(3)
        //.setTrackingRange(16)
        .clientTrackingRange(16));
        //.setShouldReceiveVelocityUpdates(true));

    private static <E extends Entity, T extends EntityType<E>> Supplier<EntityType<E>> register(final String name, final EntityType.EntityFactory<E> sup, final MobCategory classification, final Function<EntityType.Builder<E>, EntityType.Builder<E>> builder) {
        return register(name, () -> builder.apply(EntityType.Builder.of(sup, classification)).build(Util.getResource(name).toString()));
    }

    private static <E extends Entity, T extends EntityType<E>> Supplier<T> register(final String name, final Supplier<T> sup) {
        return ENTITIES.register(name, sup);
    }

    public static void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(DTNWOLF.get(), DTNWolf.createAttributes().build());
    }


}
