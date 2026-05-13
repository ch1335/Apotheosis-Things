package com.chen1335.apotheosisThings.items;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.component.BlockLocationInfo;
import com.chen1335.apotheosisThings.component.TransferConfig;
import com.chen1335.apotheosisThings.object.ATDataComponents;
import com.google.common.collect.Multimap;
import dev.shadowsoffire.apotheosis.socket.gem.storage.GemCaseTile;
import dev.shadowsoffire.apothic_enchanting.library.EnchLibraryTile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.List;

public class TransferCharm extends Item implements ICurioItem {
    public TransferCharm(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        ItemStack itemInHand = context.getItemInHand();
        BlockEntity blockEntity = level.getBlockEntity(clickedPos);
        itemInHand.update(ATDataComponents.TRANSFER_CONFIG, TransferConfig.EMPTY, transferConfig -> {
            HashMap<TransferConfig.TransferType, BlockLocationInfo> map = new HashMap<>(transferConfig.types());
            if (blockEntity instanceof GemCaseTile) {
                map.put(TransferConfig.TransferType.GEM, new BlockLocationInfo(level.dimension(), clickedPos));
            } else if (blockEntity instanceof EnchLibraryTile) {
                map.put(TransferConfig.TransferType.ENCHANTED_BOOKS, new BlockLocationInfo(level.dimension(), clickedPos));
            }
            return new TransferConfig(map);
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
        CuriosApi.addSlotModifier(modifiers, "charm", ApotheosisThings.id("transfer_charm"), 1, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.apotheosis_things.transfer_charm.desc.1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.apotheosis_things.transfer_charm.desc.2").withStyle(ChatFormatting.GRAY));
    }
}
