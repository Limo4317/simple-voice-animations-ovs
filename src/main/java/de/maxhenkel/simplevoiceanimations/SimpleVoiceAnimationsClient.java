package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class SimpleVoiceAnimationsClient implements ClientModInitializer {

    public static ClientConfig CLIENT_CONFIG;
    public static KeyEvents KEY_EVENTS;

    public static KeyMapping SETTINGS;

    @Override
    public void onInitializeClient() {
        KEY_EVENTS = new KeyEvents();

        SETTINGS = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.simplevoiceanimations.settings", GLFW.GLFW_KEY_J, KeyMapping.Category.MISC));

        Minecraft mc = Minecraft.getInstance();
        CLIENT_CONFIG = ConfigBuilder
                .builder(ClientConfig::new)
                .path(mc.gameDirectory.toPath().resolve("config").resolve(SimpleVoiceAnimations.MODID).resolve("simplevoiceanimations.properties"))
                .build();

        ClientPreferencesSync.init();
    }
}
