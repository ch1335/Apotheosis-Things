package com.chen1335.apotheosisThings.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public record TransferConfig(Map<TransferType, BlockLocationInfo> types) {
    public static final TransferConfig EMPTY = new TransferConfig(Map.of());
    public static final Codec<TransferConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(TransferType.CODEC, BlockLocationInfo.CODEC).fieldOf("types").forGetter(TransferConfig::types)
    ).apply(instance, TransferConfig::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TransferConfig> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    TransferType.STREAM_CODEC,
                    BlockLocationInfo.STREAM_CODEC
            ),
            TransferConfig::types,
            TransferConfig::new
    );

    public enum TransferType implements StringRepresentable {
        GEM(0, "gem");
        public static final Codec<TransferType> CODEC = StringRepresentable.fromValues(TransferType::values);

        public static final IntFunction<TransferType> BY_ID = ByIdMap.continuous(transferType -> transferType.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, TransferType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, transferType -> transferType.id);

        private final int id;
        private final String name;

        TransferType(int i, String name) {
            this.id = i;
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
