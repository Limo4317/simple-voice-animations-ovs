package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationData;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SimpleVoiceChatPlugin implements VoicechatPlugin {

    @Override
    public String getPluginId() {
        return SimpleVoiceAnimations.MODID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(ClientReceiveSoundEvent.EntitySound.class, this::onReceiveSound);
        registration.registerEvent(ClientSoundEvent.class, this::onOwnSound);
    }

    private void onReceiveSound(ClientReceiveSoundEvent.EntitySound event) {
        UUID id = event.getEntityId();
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        Player player = level.getPlayerByUUID(id);
        processPlayerAudio(player, event.getRawAudio());
    }

    private void onOwnSound(ClientSoundEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        processPlayerAudio(player, event.getRawAudio());
    }

    private void processPlayerAudio(Player player, short[] audio) {
        if (!(player instanceof AnimationDataExtension dataExtension)) {
            return;
        }
        AnimationData animationData = dataExtension.simplevoiceanimations$getData();
        animationData.addVoiceData(audio);
    }

}
