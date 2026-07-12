package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimationsClient;
import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientPreferencesSync {

    private static final Map<UUID, Preferences> PREFERENCES = new ConcurrentHashMap<>();

    public static void init() {
        ClientConfigurationNetworking.registerGlobalReceiver(VersionPayload.TYPE, (payload, context) -> {
            context.responseSender().sendPacket(new VersionPayload(SimpleVoiceAnimations.COMPATIBILITY_VERSION));
        });
        ClientPlayNetworking.registerGlobalReceiver(PlayerPreferencesPayload.TYPE, (payload, context) -> {
            PREFERENCES.put(payload.player(), payload.preferences());
        });
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sendLocalPreferences());
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> PREFERENCES.clear());
    }

    public static void sendLocalPreferences() {
        if (Minecraft.getInstance().getConnection() == null) {
            return;
        }
        if (!ClientPlayNetworking.canSend(UpdatePreferencesPayload.TYPE)) {
            return;
        }
        ClientPlayNetworking.send(new UpdatePreferencesPayload(SimpleVoiceAnimationsClient.CLIENT_CONFIG.getLocalPreferences()));
    }

    @Nonnull
    public static Preferences getPreferences(UUID playerId) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && playerId.equals(player.getUUID())) {
            return SimpleVoiceAnimationsClient.CLIENT_CONFIG.getLocalPreferences();
        }
        if (!PREFERENCES.containsKey(playerId)) {
            return ClientConfig.DEFAULT_PREFERENCES;
        }
        return PREFERENCES.get(playerId);
    }

}
