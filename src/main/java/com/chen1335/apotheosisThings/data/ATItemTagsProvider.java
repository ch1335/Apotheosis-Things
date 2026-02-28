package com.chen1335.apotheosisThings.data;

import com.chen1335.apotheosisThings.object.ATItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class ATItemTagsProvider extends ItemTagsProvider {
    public ATItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(CuriosTags.CHARM).add(
                ATItems.SALVAGING_CHARM.asItem(),
                ATItems.TRANSFER_CHARM.asItem()
        );
    }
}
