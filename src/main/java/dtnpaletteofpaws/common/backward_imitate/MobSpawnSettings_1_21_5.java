package dtnpaletteofpaws.common.backward_imitate;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.SpawnData;

public class MobSpawnSettings_1_21_5 {
    
    public static class SpawnerData {
        public final EntityType<?> type;
        public final int weight;
        public final int minCount;
        public final int maxCount;

        public SpawnerData(EntityType<?> type, int weight, int min, int max) {
            this.type = type;
            this.weight = weight;
            this.minCount = min;
            this.maxCount = max;
        }
    }

}
