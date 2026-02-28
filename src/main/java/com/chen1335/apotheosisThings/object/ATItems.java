package com.chen1335.apotheosisThings.object;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.items.SalvagingCharm;
import com.chen1335.apotheosisThings.items.TransferCharm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;

public class ATItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ApotheosisThings.MODID);
    public static final DeferredItem<SalvagingCharm> SALVAGING_CHARM = ITEMS.register("salvaging_charm", () -> new SalvagingCharm(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final DeferredItem<TransferCharm> TRANSFER_CHARM = ITEMS.register("transfer_charm", () -> new TransferCharm(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));

    public static Collection<DeferredHolder<Item, ? extends Item>> getEntries(){
        return ITEMS.getEntries();
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
