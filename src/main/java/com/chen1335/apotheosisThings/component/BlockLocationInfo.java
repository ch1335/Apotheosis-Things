package com.chen1335.apotheosisThings.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record BlockLocationInfo(ResourceKey<Level> dimension, BlockPos blockPos) {
    public static final Codec<BlockLocationInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(BlockLocationInfo::dimension),
            BlockPos.CODEC.fieldOf("blockPos").forGetter(BlockLocationInfo::blockPos)
    ).apply(instance, BlockLocationInfo::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BlockLocationInfo> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION),
            BlockLocationInfo::dimension,
            BlockPos.STREAM_CODEC,
            BlockLocationInfo::blockPos,
            BlockLocationInfo::new
    );
}
