package com.chen1335.apotheosisThings;

import com.chen1335.apotheosisThings.object.ATDataComponents;
import com.chen1335.apotheosisThings.object.ATItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(ApotheosisThings.MODID)
public class ApotheosisThings {
    public static final String MODID = "apotheosis_things";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> APOTHEOSIS_THINGS_TAB = CREATIVE_MODE_TABS.register("apotheosis_things_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.apotheosis_things"))
            .icon(ATItems.SALVAGING_CHARM::toStack)
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item, ? extends Item> entry : ATItems.getEntries()) {
                    output.accept(entry.value().getDefaultInstance());
                }
            }).build());


    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }

    public ApotheosisThings(IEventBus modEventBus, ModContainer modContainer) {
        CREATIVE_MODE_TABS.register(modEventBus);
        ATItems.register(modEventBus);
        ATDataComponents.register(modEventBus);
    }


}
