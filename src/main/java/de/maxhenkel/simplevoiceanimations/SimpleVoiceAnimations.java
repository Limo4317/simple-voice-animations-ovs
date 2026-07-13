package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.sync.PlayerPreferencesPayload;
import de.maxhenkel.simplevoiceanimations.sync.ServerPreferencesSync;
import de.maxhenkel.simplevoiceanimations.sync.UpdatePreferencesPayload;
import de.maxhenkel.simplevoiceanimations.sync.VersionPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleVoiceAnimations implements ModInitializer {

    public static final String MODID = "simplevoiceanimations";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final int COMPATIBILITY_VERSION = 2;

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.clientboundConfiguration().register(VersionPayload.TYPE, VersionPayload.CODEC);
        PayloadTypeRegistry.serverboundConfiguration().register(VersionPayload.TYPE, VersionPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(UpdatePreferencesPayload.TYPE, UpdatePreferencesPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PlayerPreferencesPayload.TYPE, PlayerPreferencesPayload.CODEC);
        ServerPreferencesSync.init();
    }

}
