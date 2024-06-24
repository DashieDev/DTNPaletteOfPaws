package dtnpaletteofpaws.client.data;

import java.util.function.Supplier;

import dtnpaletteofpaws.DTNItems;
import dtnpaletteofpaws.common.lib.Constants;
import dtnpaletteofpaws.common.util.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class DTNItemModelProvider extends ItemModelProvider {

    public DTNItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        defaultItemModel(DTNItems.WOLF_CHARM);
    }
    
    private ItemModelBuilder defaultItemModel(Supplier<Item> item_sup) {
        var item = item_sup.get();
        var item_name = ForgeRegistries.ITEMS.getKey(item).getPath();
        var item_texture = Util.getResource(ModelProvider.ITEM_FOLDER + "/" + item_name);
        var builer = withExistingParent(item_name, ModelProvider.ITEM_FOLDER + "/generated")
            .texture("layer0", item_texture);
        return builer;
    }

}
