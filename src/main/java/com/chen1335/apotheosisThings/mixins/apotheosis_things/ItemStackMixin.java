package com.chen1335.apotheosisThings.mixins.apotheosis_things;

import com.chen1335.apotheosisThings.object.ATDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    @Nullable
    public abstract <T> T set(DataComponentType<? super T> component, @org.jetbrains.annotations.Nullable T value);

    @Shadow
    public abstract int getMaxStackSize();

    @Inject(method = "onCraftedBy", at = @At("HEAD"))
    private void onCraftedBy(Level level, Player player, int amount, CallbackInfo ci) {
        if (this.getMaxStackSize() == 1) {
            set(ATDataComponents.CAN_AUTO_SALVAGING.value(), false);
        }
    }
}
