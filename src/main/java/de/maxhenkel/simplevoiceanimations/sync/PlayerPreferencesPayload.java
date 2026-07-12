package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public record PlayerPreferencesPayload(UUID player, Preferences preferences) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PlayerPreferencesPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SimpleVoiceAnimations.MODID, "player_preferences"));
    public static final StreamCodec<FriendlyByteBuf, PlayerPreferencesPayload> CODEC = CustomPacketPayload.codec(PlayerPreferencesPayload::write, PlayerPreferencesPayload::read);

    private static PlayerPreferencesPayload read(FriendlyByteBuf buffer) {
        return new PlayerPreferencesPayload(buffer.readUUID(), Preferences.read(buffer));
    }

    private void write(FriendlyByteBuf buffer) {
        buffer.writeUUID(player);
        preferences.write(buffer);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
