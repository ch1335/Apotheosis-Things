package com.chen1335.apotheosisThings.items;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.component.SalvagingCharmConfig;
import com.chen1335.apotheosisThings.object.ATDataComponents;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SalvagingCharm extends Item implements ICurioItem {
    public SalvagingCharm(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.apotheosis_things.salvaging_charm.desc.1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.apotheosis_things.salvaging_charm.desc.2").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.apotheosis_things.salvaging_charm.desc.3").withStyle(ChatFormatting.GRAY));
        SalvagingCharmConfig salvagingCharmConfig = stack.get(ATDataComponents.SALVAGING_CHARM_CONFIG);
        if (salvagingCharmConfig != null) {
            tooltipComponents.add(Component.translatable("item.apotheosis_things.salvaging_charm.desc.4", salvagingCharmConfig.rarity().toComponent()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
        CuriosApi.addSlotModifier(modifiers, "charm", ApotheosisThings.id("salvaging_charm"), 1, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}
