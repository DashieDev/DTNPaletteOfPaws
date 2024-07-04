package dtnpaletteofpaws;

import java.util.function.Supplier;

import dtnpaletteofpaws.common.event.WolfCharmItem;
import dtnpaletteofpaws.common.lib.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DTNItems {
    
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);
    
    public static final Supplier<Item> WOLF_CHARM = registerWolfCharm("wolf_charm");

    private static Supplier<Item> registerWolfCharm(String name) {
        return ITEM.register(name, WolfCharmItem::new);
    }


}
