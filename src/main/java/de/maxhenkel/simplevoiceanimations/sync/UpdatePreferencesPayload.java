package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record UpdatePreferencesPayload(Preferences preferences) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdatePreferencesPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SimpleVoiceAnimations.MODID, "update_preferences"));
    public static final StreamCodec<FriendlyByteBuf, UpdatePreferencesPayload> CODEC = CustomPacketPayload.codec(UpdatePreferencesPayload::write, UpdatePreferencesPayload::read);

    private static UpdatePreferencesPayload read(FriendlyByteBuf buffer) {
        return new UpdatePreferencesPayload(Preferences.read(buffer));
    }

    private void write(FriendlyByteBuf buffer) {
        preferences.write(buffer);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
