package dtnpaletteofpaws.common.variant;

import java.util.Optional;

import dtnpaletteofpaws.common.entity.DTNWolf;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class WolfVariant {
    
    private final ResourceLocation id;
    private final ResourceLocation textureLoc;
    private final ResourceLocation wildTextureLoc;
    private final String translationKey;
    private Optional<ResourceLocation> glowingOverlay;
    private Optional<ResourceLocation> wildGlowingOverlay;
    private boolean fireImmune;
    private boolean fallImmune;
    private boolean swimUnderwater;
    
    public WolfVariant(Props props) {
        this.id = props.name;
        this.textureLoc = createTextureLoc(props.name);
        this.wildTextureLoc = createWildTextureLoc(props.name);
        this.translationKey = createTranslationKey(props.name);
        this.glowingOverlay = props.glowingOverlay;
        this.wildGlowingOverlay = props.glowingOverlay_wild;
        this.fireImmune = props.fireImmune;
        this.fallImmune = props.fallImmune;
        this.swimUnderwater = props.swimUnderwater;
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

    public Optional<ResourceLocation> glowingOverlay(boolean wild) {
        if (wild)
            return this.wildGlowingOverlay;
        return this.glowingOverlay;
    }

    public boolean fireImmune() {
        return this.fireImmune;
    }

    public boolean fallImmune() {
        return this.fallImmune;
    }

    public boolean swimUnderwater() {
        return this.swimUnderwater;
    }

    public void tickWolf(DTNWolf wolf) {}

    public static Props props(String name) {
        return new Props(Util.getResource(name));
    }

    public static Props builder(ResourceLocation name) {
        return new Props(name);
    }

    public static class Props {
        
        private final ResourceLocation name;
        private boolean fireImmune;
        private boolean fallImmune;
        private boolean swimUnderwater;
        private Optional<ResourceLocation> glowingOverlay = Optional.empty();
        private Optional<ResourceLocation> glowingOverlay_wild = Optional.empty();

        private Props(ResourceLocation name) {
            this.name = name;
        }
        
        public Props fireImmune() {
            this.fireImmune = true;
            return this;
        }

        public Props fallImmune() {
            this.fallImmune = true;
            return this;
        }

        public Props swimUnderwater() {
            this.swimUnderwater = true;
            return this;
        }

        public Props glowingOverlay(ResourceLocation overlay, ResourceLocation wild_overlay) {
            if (overlay == null)
                return this;
            this.glowingOverlay = Optional.of(overlay);
            this.glowingOverlay_wild = Optional.of(wild_overlay);
            return this;
        }

    }
}
