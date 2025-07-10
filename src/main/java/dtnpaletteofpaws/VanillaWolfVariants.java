package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.util.Util;
import dtnpaletteofpaws.common.variant.WolfVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;

//To provide the ability to spawn the new wolf variants in 1.20.4 and under to be used with DTN.
public class VanillaWolfVariants {
        
    public static final DeferredRegister<WolfVariant> VANILLA_WOLF_VARIANT = DeferredRegister.create(DTNRegistries.Keys.DTN_WOLF_VARIANT, "minecraft");

    //Minecraft's
    public static final Supplier<WolfVariant> PALE = registerVanilla("pale");
    public static final Supplier<WolfVariant> CHESTNUT = registerVanilla("chestnut");
    public static final Supplier<WolfVariant> STRIPED = registerVanilla("striped");
    public static final Supplier<WolfVariant> WOOD = registerVanilla("woods");
    public static final Supplier<WolfVariant> RUSTY = registerVanilla("rusty");
    public static final Supplier<WolfVariant> BLACK = registerVanilla("black");
    public static final Supplier<WolfVariant> SNOWY = registerVanilla("snowy");
    public static final Supplier<WolfVariant> ASHEN = registerVanilla("ashen");
    public static final Supplier<WolfVariant> SPOTTED = registerVanilla("spotted");

    private static Supplier<WolfVariant> registerVanilla(String name) {
        var props = WolfVariant.builder(Util.getVanillaResource(name));
        final var captured_variant = new VanillaWolfVariant(props);
        return VANILLA_WOLF_VARIANT.register(name, () -> captured_variant);
    }

    public static class VanillaWolfVariant extends WolfVariant {

        public VanillaWolfVariant(Props props) {
            super(props);
        }

        @Override
        public ResourceLocation createTextureLoc(ResourceLocation name) {
            return Util.getResource(
                "textures/entity/dtnwolf/variants/vanilla/wolf_" + name.getPath() + ".png");
        }

        @Override
        public ResourceLocation createWildTextureLoc(ResourceLocation name) {
            return Util.getResource(
                "textures/entity/dtnwolf/variants/vanilla/wolf_" + name.getPath() + "_wild.png");
        }

    }

}
