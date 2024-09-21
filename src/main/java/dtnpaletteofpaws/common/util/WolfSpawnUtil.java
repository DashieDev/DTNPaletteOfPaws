package dtnpaletteofpaws.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfigs;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class WolfSpawnUtil {
    
    public static boolean isNetherOrEndSpawn(LevelAccessor level_accessor) {
        if (!(level_accessor instanceof ServerLevelAccessor s_level_accessor))
            return false;
        
        var inner_level = s_level_accessor.getLevel();
        if (inner_level == null)
            return false;
        var dim = inner_level.dimension();
        
        if (!dim.equals(Level.NETHER) && !dim.equals(Level.END))
            return false;
        
        return true;
    }


}
