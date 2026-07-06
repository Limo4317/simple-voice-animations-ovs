package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.configbuilder.ConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class SimpleVoiceAnimations implements ClientModInitializer {

    public static final String MODID = "simplevoiceanimations";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
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
                .path(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("simplevoiceanimations.properties"))
                .build();
    }
}
