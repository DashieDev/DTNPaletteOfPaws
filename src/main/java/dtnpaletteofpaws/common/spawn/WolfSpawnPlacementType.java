package dtnpaletteofpaws.common.spawn;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Functions;
import com.mojang.serialization.Codec;

public enum WolfSpawnPlacementType {

    GROUND("ground"), WATER("water"),
    UNDERGROUND("underground");

    private final String id;

    private WolfSpawnPlacementType(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }
    
    public static WolfSpawnPlacementType fromId(String id) {
        for (var val : WolfSpawnPlacementType.values()) {
            if (val.id().equals(id))
                return val;
        }

        return WolfSpawnPlacementType.GROUND;
    }

    public static final Codec<WolfSpawnPlacementType> CODEC = 
        Codec.STRING.xmap(WolfSpawnPlacementType::fromId, WolfSpawnPlacementType::id);

}
