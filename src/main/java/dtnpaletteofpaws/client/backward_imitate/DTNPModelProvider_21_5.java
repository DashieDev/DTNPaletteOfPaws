package dtnpaletteofpaws.client.backward_imitate;

import java.util.function.Supplier;

import dtnpaletteofpaws.client.data.DTNItemModelProvider;
import dtnpaletteofpaws.common.lib.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class DTNPModelProvider_21_5 extends ModelProvider {

    public DTNPModelProvider_21_5(PackOutput p_388260_) {
        super(p_388260_, Constants.MOD_ID);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerItem(itemModels);
    }

    private void registerItem(ItemModelGenerators itemModels) {
        var prov = new DTNItemModelProvider();
        prov.itemGenerators_1_21_5 = itemModels;
        prov.registerModels();
        prov.itemGenerators_1_21_5 = null;
    }

    public static void generated(ItemModelGenerators itemModels, Supplier<? extends Item> item_supplier) {
        var item = item_supplier.get();
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }


}
