package com.chen1335.apotheosisThings.mixins.sophisticatedcore;

import com.chen1335.apotheosisThings.util.Util;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.upgrades.magnet.MagnetUpgradeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagnetUpgradeWrapper.class)
public class MagnetUpgradeWrapperMixin {
    @Inject(method = "tryToInsertItem", at = @At("HEAD"), cancellable = true)
    private void tryToInsertItem(Player player, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> cir) {
        Util.handleSalvaging(player, itemEntity);
        Util.handleTransfer(player, itemEntity);
        if (itemEntity.getItem().isEmpty()) {
            cir.setReturnValue(false);
        }
    }
}
