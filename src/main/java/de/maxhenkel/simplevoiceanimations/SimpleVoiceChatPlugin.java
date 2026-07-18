package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class SimpleVoiceChatPlugin implements VoicechatPlugin {

    @Override
    public String getPluginId() {
        return SimpleVoiceAnimations.MODID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        if (!FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            return;
        }
        registration.registerEvent(ClientReceiveSoundEvent.EntitySound.class, ClientVoiceChatEvents::onReceiveSound);
        registration.registerEvent(ClientSoundEvent.class, ClientVoiceChatEvents::onOwnSound);
    }

}
