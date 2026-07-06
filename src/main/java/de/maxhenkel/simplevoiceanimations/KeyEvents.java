package de.maxhenkel.simplevoiceanimations;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class KeyEvents {

    private static final Minecraft mc = Minecraft.getInstance();

    public KeyEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player == null) {
                return;
            }
            if (SimpleVoiceAnimations.SETTINGS.consumeClick()) {
                //TODO Implement cloth config GUI
            }
        });
    }

}
