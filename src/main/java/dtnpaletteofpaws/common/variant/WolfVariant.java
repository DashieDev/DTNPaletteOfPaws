package dtnpaletteofpaws.common.variant;

import java.util.Optional;

import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class WolfVariant {
    
    private final ResourceLocation id;
    private final ResourceLocation textureLoc;
    private final ResourceLocation wildTextureLoc;
    private final String translationKey;
    private Optional<ResourceLocation> glowingOverlay;
    private boolean fireImmune;
    
    private WolfVariant(ResourceLocation name) {
        this.id = name;
        this.textureLoc = createTextureLoc(name);
        this.wildTextureLoc = createWildTextureLoc(name);
        this.translationKey = createTranslationKey(name);
    }

    private static ResourceLocation createTextureLoc(ResourceLocation name) {
        return Util.modifyPath(name, x -> "textures/entity/dtnwolf/variants/wolf_" + x + ".png");
    }

    private static ResourceLocation createWildTextureLoc(ResourceLocation name) {
        return Util.modifyPath(name, x -> "textures/entity/dtnwolf/variants/wolf_" + x + "_wild.png");
    }

    private static String createTranslationKey(ResourceLocation name) {
        return Constants.MOD_ID + ".variant." + name.getPath();
    }

    public ResourceLocation id() {
        return this.id;
    }

    public ResourceLocation texture() {
        return this.textureLoc;
    }

    public ResourceLocation wildTexture() {
        return this.wildTextureLoc;
    }

    public String translationKey() {
        return this.translationKey;
    }

    public Optional<ResourceLocation> glowingOverlay() {
        return this.glowingOverlay;
    }

    public boolean fireImmune() {
        return this.fireImmune;
    }

    public static Builder builder(String name) {
        return new Builder(Util.getResource(name));
    }

    public static Builder builder(ResourceLocation name) {
        return new Builder(name);
    }

    public static class Builder {
        
        private final ResourceLocation name;
        private boolean fireImmune;
        private Optional<ResourceLocation> glowingOverlay = Optional.empty();

        private Builder(ResourceLocation name) {
            this.name = name;
        }
        
        public Builder fireImmune() {
            this.fireImmune = true;
            return this;
        }

        public Builder glowingOverlay(ResourceLocation overlay) {
            if (overlay == null)
                return this;
            this.glowingOverlay = Optional.of(overlay);
            return this;
        }

        public WolfVariant build() {
            var ret = new WolfVariant(name);
            ret.glowingOverlay = this.glowingOverlay;
            ret.fireImmune = this.fireImmune;
            return ret;
        }

    }
}
