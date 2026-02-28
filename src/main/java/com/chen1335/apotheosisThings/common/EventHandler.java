package com.chen1335.apotheosisThings.common;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.component.SalvagingCharmConfig;
import com.chen1335.apotheosisThings.object.ATDataComponents;
import com.chen1335.apotheosisThings.object.ATItems;
import com.chen1335.apotheosisThings.util.Util;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvagingMenu;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import java.lang.reflect.Field;
import java.util.List;

public class EventHandler {
    @EventBusSubscriber(modid = ApotheosisThings.MODID)
    public static class GAME {
        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void ItemEntityPickupEvent(ItemEntityPickupEvent.Pre event) {
            handleSalvaging(event);
            handleTransfer(event);
        }

        private static void handleTransfer(ItemEntityPickupEvent.Pre event) {
            Util.handleTransfer(event.getPlayer(), event.getItemEntity());
        }


        private static void handleSalvaging(ItemEntityPickupEvent.Pre event) {
            ItemEntity itemEntity = event.getItemEntity();
            Util.handleSalvaging(event.getPlayer(), itemEntity);
            if (itemEntity.getItem().isEmpty()) {
                event.setCanPickup(TriState.FALSE);
                event.getItemEntity().discard();
            }
        }

        @SubscribeEvent
        public static void LivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
            Item item = event.getTo().getItem();
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                if (item.getDefaultMaxStackSize() == 1 && event.getTo().getOrDefault(ATDataComponents.CAN_AUTO_SALVAGING, true)) {
                    List<ItemStack> itemStacks = SalvagingMenu.getSalvageResults(player.level(), event.getTo());
                    if (!itemStacks.isEmpty()) {
                        event.getTo().set(ATDataComponents.CAN_AUTO_SALVAGING, false);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void AnvilUpdateEvent(AnvilUpdateEvent event) throws
                NoSuchFieldException, IllegalAccessException {
            if (event.getLeft().is(ATItems.SALVAGING_CHARM) && event.getRight().getItem() instanceof SalvageItem salvageItem) {
                ItemStack left = event.getLeft();
                Field declaredField = SalvageItem.class.getDeclaredField("rarity");
                declaredField.setAccessible(true);
                DynamicHolder<LootRarity> rarity = ((DynamicHolder<LootRarity>) declaredField.get(salvageItem));
                left.set(ATDataComponents.SALVAGING_CHARM_CONFIG, new SalvagingCharmConfig(rarity.get()));
                event.setOutput(left);
                event.setCost(5);
                event.setMaterialCost(1);
            }
        }
    }
}
