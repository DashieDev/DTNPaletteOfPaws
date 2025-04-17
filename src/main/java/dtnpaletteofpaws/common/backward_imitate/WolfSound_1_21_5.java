package dtnpaletteofpaws.common.backward_imitate;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariant;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.level.Level;

public class WolfSound_1_21_5 {
    
    public static WolfSoundVariant getClassic(Level level) {
        var sound_variant = level.registryAccess().lookupOrThrow(Registries.WOLF_SOUND_VARIANT)
            .getValue(WolfSoundVariants.CLASSIC);
        return sound_variant;
    }

    public static SoundEvent getAmbientSound(Level level) {
        return getClassic(level).ambientSound().value();
    }

    public static SoundEvent getHurtSound(Level level) {
        return getClassic(level).hurtSound().value();
    }

    public static SoundEvent getWhineSound(Level level) {
        return getClassic(level).whineSound().value();
    }

    public static SoundEvent getPantSound(Level level) {
        return getClassic(level).pantSound().value();
    }

    public static SoundEvent getDeathSound(Level level) {
        return getClassic(level).deathSound().value();
    }

}
