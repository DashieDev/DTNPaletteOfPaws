package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.serializer.WolfVariantSerializer;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DTNSerializers {
    
    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);
    public static final EntityDataSerializer<WolfVariant> DTN_WOLF_VARIANT = register("dtn_wolf_variant", new WolfVariantSerializer());

    public static <T> EntityDataSerializer<T> register(String name, EntityDataSerializer<T> ser) {
        final var captured_ser = ser;
        SERIALIZERS.register(name, () -> captured_ser);
        return ser;
    }


}
