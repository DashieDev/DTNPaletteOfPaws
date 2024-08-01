package dtnpaletteofpaws.dtn_support.variant;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import doggytalents.DogVariants;
import doggytalents.common.variant.DogVariant;
import doggytalents.common.variant.util.DogVariantUtil;
import dtnpaletteofpaws.WolfVariants;
import dtnpaletteofpaws.common.variant.WolfVariant;

public class DTNDogVariantMapping {
    
    private static Map<WolfVariant, DogVariant> MAPPING = null;

    public static void init() {
        MAPPING = ImmutableMap.<WolfVariant, DogVariant>builder()
            .put(WolfVariants.CHERRY.get(), DogVariants.CHERRY.get())
            .put(WolfVariants.LEMONY_LIME.get(), DogVariants.LEMONY_LIME.get())
            .put(WolfVariants.HIMALAYAN_SALT.get(), DogVariants.HIMALAYAN_SALT.get())
            .put(WolfVariants.BAMBOO.get(), DogVariants.BAMBOO.get())
            .put(WolfVariants.CRIMSON.get(), DogVariants.CRIMSON.get())
            .put(WolfVariants.BIRCH.get(), DogVariants.BIRCH.get())
            .put(WolfVariants.WARPED.get(), DogVariants.WARPED.get())
            .put(WolfVariants.PISTACHIO.get(), DogVariants.PISTACHIO.get())
            .put(WolfVariants.GUACAMOLE.get(), DogVariants.GUACAMOLE.get())
            .put(WolfVariants.MOLTEN.get(), DogVariants.MOLTEN.get())
            .put(WolfVariants.YUZU.get(), DogVariants.YUZU.get())
            .put(WolfVariants.CAPPUCCINO.get(), DTNSupportDogVariants.CAPPUCCINO.get())
            .put(WolfVariants.ESPRESSO.get(), DTNSupportDogVariants.ESPRESSO.get())
            .put(WolfVariants.LATTE.get(), DTNSupportDogVariants.LATTE.get())
            .put(WolfVariants.MOCHA.get(), DTNSupportDogVariants.MOCHA.get())
            .put(WolfVariants.WITHERED_SOUL.get(), DTNSupportDogVariants.WITHERED_SOUL.get())
            .put(WolfVariants.MUSHROOM_BROWN.get(), DTNSupportDogVariants.MUSHROOM_BROWN.get())
            .put(WolfVariants.MUSHROOM_RED.get(), DTNSupportDogVariants.MUSHROOM_RED.get())
            .put(WolfVariants.BONITO_FLAKES.get(), DTNSupportDogVariants.BONITO_FLAKES.get())
            .put(WolfVariants.KOMBU.get(), DTNSupportDogVariants.KOMBU.get())
            .put(WolfVariants.SHITAKE.get(), DTNSupportDogVariants.SHITAKE.get())
            .put(WolfVariants.ENOKI.get(), DTNSupportDogVariants.ENOKI.get())
            .put(WolfVariants.STRAWBERRY.get(), DTNSupportDogVariants.STRAWBERRY.get())
            .put(WolfVariants.CHOCOLATE.get(), DTNSupportDogVariants.CHOCOLATE.get())
            .put(WolfVariants.VANILLA.get(), DTNSupportDogVariants.VANILLA.get())
            .put(WolfVariants.WALNUT.get(), DTNSupportDogVariants.WALNUT.get())
            .build();
    }

    public static DogVariant getDTNWolf(WolfVariant variant) {
        return MAPPING.getOrDefault(variant, DogVariantUtil.getDefault());
    } 

}
