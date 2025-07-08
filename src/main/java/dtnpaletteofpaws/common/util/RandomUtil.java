package dtnpaletteofpaws.common.util;

import java.util.List;
import java.util.Optional;

import net.minecraft.util.RandomSource;

public class RandomUtil {
    
    public static float nextFloatRemapped(RandomSource random) {
        return random.nextFloat() * 2 - 1;
    }

    public static <T> Optional<T> getRandomItem(RandomSource random, List<T> list) {
        if (list.isEmpty())
            return Optional.empty();
        int size = list.size();
        if (size == 1)
            return Optional.of(list.get(0));
        int r = random.nextInt(size);
        return Optional.of(list.get(r));
    }

}
