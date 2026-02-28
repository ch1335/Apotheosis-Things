package com.chen1335.apotheosisThings.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.shadowsoffire.apotheosis.loot.LootRarity;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record SalvagingCharmConfig(LootRarity rarity) {
    public static final Codec<SalvagingCharmConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LootRarity.CODEC.fieldOf("highest_rarity").forGetter(SalvagingCharmConfig::rarity)
    ).apply(instance, SalvagingCharmConfig::new));


    public static final StreamCodec<RegistryFriendlyByteBuf, SalvagingCharmConfig> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC.map(RarityRegistry.INSTANCE::getValue, RarityRegistry.INSTANCE::getKey),
            SalvagingCharmConfig::rarity,
            SalvagingCharmConfig::new
    );
}
