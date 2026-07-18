package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimations;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ConfigurationTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ServerPreferencesSync {

    private static final ConfigurationTask.Type VERSION_TASK = new ConfigurationTask.Type("%s:version".formatted(SimpleVoiceAnimations.MODID));
    private static final Map<UUID, Preferences> PREFERENCES = new ConcurrentHashMap<>();

    public static void init() {
        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            // Clients without the mod never see the version handshake, so vanilla clients can still connect
            if (ServerConfigurationNetworking.canSend(handler, VersionPayload.TYPE)) {
                handler.addTask(new VersionConfigurationTask());
            }
        });
        ServerConfigurationNetworking.registerGlobalReceiver(VersionPayload.TYPE, (payload, context) -> {
            if (payload.version() != SimpleVoiceAnimations.COMPATIBILITY_VERSION) {
                context.packetListener().disconnect(Component.translatable("message.simplevoiceanimations.incompatible"));
                SimpleVoiceAnimations.LOGGER.warn("Incompatible Simple Voice Animations version (server compatibility version {}, client compatibility version {})", SimpleVoiceAnimations.COMPATIBILITY_VERSION, payload.version());
                return;
            }
            context.packetListener().completeTask(VERSION_TASK);
        });
        ServerPlayNetworking.registerGlobalReceiver(UpdatePreferencesPayload.TYPE, (payload, context) -> {
            broadcastPreferences(context.server(), context.player().getUUID(), payload.preferences());
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (!ServerPlayNetworking.canSend(handler.player, PlayerPreferencesPayload.TYPE)) {
                // Players without the mod get the animation style configured in the server config
                broadcastPreferences(server, handler.player.getUUID(), SimpleVoiceAnimations.SERVER_CONFIG.getDefaultPreferences());
                return;
            }
            PREFERENCES.forEach((uuid, preferences) -> sender.sendPacket(new PlayerPreferencesPayload(uuid, preferences)));
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> PREFERENCES.remove(handler.player.getUUID()));
    }

    private static void broadcastPreferences(MinecraftServer server, UUID playerId, Preferences preferences) {
        PREFERENCES.put(playerId, preferences);
        PlayerPreferencesPayload update = new PlayerPreferencesPayload(playerId, preferences);
        for (ServerPlayer player : PlayerLookup.all(server)) {
            if (ServerPlayNetworking.canSend(player, PlayerPreferencesPayload.TYPE)) {
                ServerPlayNetworking.send(player, update);
            }
        }
    }

    private static class VersionConfigurationTask implements ConfigurationTask {

        @Override
        public void start(Consumer<Packet<?>> connection) {
            connection.accept(ServerConfigurationNetworking.createClientboundPacket(new VersionPayload(SimpleVoiceAnimations.COMPATIBILITY_VERSION)));
        }

        @Override
        public Type type() {
            return VERSION_TASK;
        }

    }

}
