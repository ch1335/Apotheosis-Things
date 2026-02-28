package com.chen1335.apotheosisThings.object;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.component.SalvagingCharmConfig;
import com.chen1335.apotheosisThings.component.TransferConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ATDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ApotheosisThings.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CAN_AUTO_SALVAGING = DATA_COMPONENTS.register("can_auto_salvaging", () -> new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TransferConfig>> TRANSFER_CONFIG = DATA_COMPONENTS.register("transfer_config", () -> new DataComponentType.Builder<TransferConfig>().persistent(TransferConfig.CODEC).networkSynchronized(TransferConfig.STREAM_CODEC).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SalvagingCharmConfig>> SALVAGING_CHARM_CONFIG = DATA_COMPONENTS.register("salvaging_charm_config", () -> new DataComponentType.Builder<SalvagingCharmConfig>().persistent(SalvagingCharmConfig.CODEC).networkSynchronized(SalvagingCharmConfig.STREAM_CODEC).build());

    public static void register(IEventBus bus) {
        DATA_COMPONENTS.register(bus);
    }
}
