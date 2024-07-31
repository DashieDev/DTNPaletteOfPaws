package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.serializer.DogSerializer;
import dtnpaletteofpaws.common.serializer.WolfVariantSerializer;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DTNSerializers {
    
    //public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);
    public static final DogSerializer<WolfVariant> DTN_WOLF_VARIANT = register("dtn_wolf_variant", new WolfVariantSerializer());

    public static <T> DogSerializer<T> register(String name, DogSerializer<T> ser) {
        final var captured_ser = ser;
        //SERIALIZERS.register(name, () -> captured_ser);
        return ser;
    }


}
