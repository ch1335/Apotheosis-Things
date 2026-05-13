package com.chen1335.apotheosisThings.util;

import com.chen1335.apotheosisThings.component.BlockLocationInfo;
import com.chen1335.apotheosisThings.component.SalvagingCharmConfig;
import com.chen1335.apotheosisThings.component.TransferConfig;
import com.chen1335.apotheosisThings.object.ATDataComponents;
import com.chen1335.apotheosisThings.object.ATItems;
import dev.shadowsoffire.apotheosis.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvagingMenu;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.apotheosis.socket.gem.GemItem;
import dev.shadowsoffire.apotheosis.socket.gem.UnsocketedGem;
import dev.shadowsoffire.apotheosis.socket.gem.storage.GemCaseTile;
import dev.shadowsoffire.apothic_enchanting.library.EnchLibraryTile;
import dev.shadowsoffire.placebo.reload.DynamicHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.neoforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class Util {
    public static void handleSalvaging(Player player, ItemEntity entity) {
        if (entity.getItem().isEmpty()) {
            return;
        }

        if (entity.getItem().getItem() instanceof GemItem) {
            return;
        }


        boolean canSalvaging = entity.getItem().getOrDefault(ATDataComponents.CAN_AUTO_SALVAGING, true);
        if (canSalvaging) {
            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                List<SlotResult> curios = iCuriosItemHandler.findCurios(ATItems.SALVAGING_CHARM.asItem());
                if (!curios.isEmpty()) {
                    DynamicHolder<LootRarity> rarity = AffixHelper.getRarity(entity.getItem());
                    SalvagingCharmConfig salvagingCharmConfig = curios.getFirst().stack().get(ATDataComponents.SALVAGING_CHARM_CONFIG);
                    if (salvagingCharmConfig == null) {
                        return;
                    }
                    if (rarity.isBound() && salvagingCharmConfig.rarity().sortIndex() < rarity.get().sortIndex()) {
                        return;
                    }

                    ItemStack item = entity.getItem();
                    List<ItemStack> itemStacks = SalvagingMenu.getSalvageResults(entity.level(), item);
                    if (!itemStacks.isEmpty()) {
                        for (int i = 0; i < item.getCount(); i++) {
                            for (ItemStack itemStack : itemStacks) {
                                ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getX(), itemStack);
                                itemEntity.setNoPickUpDelay();
                                entity.level().addFreshEntity(itemEntity);
                                itemEntity.playerTouch(player);
                            }
                        }
                        entity.setItem(ItemStack.EMPTY);
                        Level level = player.level();
                        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS, 0.99F, level.random.nextFloat() * 0.25F + 1F);
                        level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_CLUSTER_STEP, SoundSource.BLOCKS, 0.34F, level.random.nextFloat() * 0.2F + 0.8F);
                        level.playSound(null, player.blockPosition(), SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 0.45F, level.random.nextFloat() * 0.5F + 0.75F);
                    }
                }
            });
        }
    }

    public static void handleTransfer(Player player, ItemEntity entity) {
        if (entity.level().isClientSide) {
            return;
        }
        if (entity.getItem().getItem() instanceof GemItem) {
            handleTransferGems(player, entity);
        } else if (entity.getItem().getItem() instanceof EnchantedBookItem) {
            handleTransferEnchantedBooks(player, entity);
        }
    }

    private static void handleTransferEnchantedBooks(Player player, ItemEntity entity) {
        ServerLevel serverLevel = (ServerLevel) entity.level();
        CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
            List<SlotResult> curios = iCuriosItemHandler.findCurios(ATItems.TRANSFER_CHARM.asItem());
            if (!curios.isEmpty()) {
                ItemStack stack = curios.getFirst().stack();
                TransferConfig transferConfig = stack.get(ATDataComponents.TRANSFER_CONFIG);
                if (transferConfig != null) {
                    BlockLocationInfo blockLocationInfo = transferConfig.types().get(TransferConfig.TransferType.ENCHANTED_BOOKS);
                    if (blockLocationInfo != null) {
                        ResourceKey<Level> dimension = blockLocationInfo.dimension();
                        BlockPos blockPos = blockLocationInfo.blockPos();
                        ServerLevel level = serverLevel.getServer().getLevel(dimension);
                        if (level != null) {
                            ChunkAccess chunk = level.getChunk(SectionPos.blockToSectionCoord(blockPos.getX()), SectionPos.blockToSectionCoord(blockPos.getZ()), ChunkStatus.FULL, false);
                            if (chunk != null) {
                                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                                if (blockEntity instanceof EnchLibraryTile enchLibraryTile && entity.getItem().getItem() instanceof EnchantedBookItem) {
                                    for (int i = 0; i < entity.getItem().getCount(); i++) {
                                        enchLibraryTile.depositBook(entity.getItem());
                                    }
                                    entity.getItem().shrink(entity.getItem().getCount());
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public static void handleTransferGems(Player player, ItemEntity entity) {
        ServerLevel serverLevel = (ServerLevel) entity.level();
        CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
            List<SlotResult> curios = iCuriosItemHandler.findCurios(ATItems.TRANSFER_CHARM.asItem());
            if (!curios.isEmpty()) {
                ItemStack stack = curios.getFirst().stack();
                TransferConfig transferConfig = stack.get(ATDataComponents.TRANSFER_CONFIG);
                if (transferConfig != null) {
                    BlockLocationInfo blockLocationInfo = transferConfig.types().get(TransferConfig.TransferType.GEM);
                    if (blockLocationInfo != null) {
                        ResourceKey<Level> dimension = blockLocationInfo.dimension();
                        BlockPos blockPos = blockLocationInfo.blockPos();
                        ServerLevel level = serverLevel.getServer().getLevel(dimension);
                        if (level != null) {
                            ChunkAccess chunk = level.getChunk(SectionPos.blockToSectionCoord(blockPos.getX()), SectionPos.blockToSectionCoord(blockPos.getZ()), ChunkStatus.FULL, false);
                            if (chunk != null) {
                                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                                if (blockEntity instanceof GemCaseTile gemCaseTile) {
                                    UnsocketedGem gem = UnsocketedGem.of(entity.getItem());
                                    if (!gem.isValid()) return;
                                    int count = gemCaseTile.getCount(gem.gem(), gem.purity());
                                    IItemHandler itemHandler = gemCaseTile.getItemHandler(Direction.UP);
                                    int slotLimit = itemHandler.getSlotLimit(1);
                                    int insertCount = Math.min(entity.getItem().getCount(), slotLimit - count);
                                    gemCaseTile.depositGem(entity.getItem().copyWithCount(insertCount));
                                    entity.getItem().shrink(insertCount);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
