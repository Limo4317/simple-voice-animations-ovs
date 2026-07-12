package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record VersionPayload(int version) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<VersionPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SimpleVoiceAnimations.MODID, "version"));
    public static final StreamCodec<FriendlyByteBuf, VersionPayload> CODEC = CustomPacketPayload.codec(VersionPayload::write, VersionPayload::read);

    private static VersionPayload read(FriendlyByteBuf buffer) {
        return new VersionPayload(buffer.readVarInt());
    }

    private void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(version);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
