package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationData;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class ClientVoiceChatEvents {

    public static void onReceiveSound(ClientReceiveSoundEvent event) {
        UUID id = event.getId();
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        Player player = level.getPlayerByUUID(id);
        if (player == null) {
            return;
        }
        processPlayerAudio(player, event.getRawAudio());
    }

    public static void onOwnSound(ClientSoundEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        processPlayerAudio(player, event.getRawAudio());
    }

    private static void processPlayerAudio(Player player, short[] audio) {
        if (!(player instanceof AnimationDataExtension dataExtension)) {
            return;
        }
        AnimationData animationData = dataExtension.simplevoiceanimations$getData();
        animationData.addVoiceData(audio);
    }

}
