package com.chen1335.apotheosisThings.data;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.object.ATItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ATItemModelProvider extends ItemModelProvider {
    public ATItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ApotheosisThings.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        basicItem(ATItems.TRANSFER_CHARM.asItem());
        basicItem(ATItems.SALVAGING_CHARM.asItem());
    }
}
