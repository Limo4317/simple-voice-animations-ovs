package de.maxhenkel.simplevoiceanimations.interfaces;

import de.maxhenkel.simplevoiceanimations.utils.AudioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class AnimationData {

    public float targetVolume;
    private float currentRenderScale = 1F;
    private long lastUpdate = 0L;

    public void addVoiceData(short[] audio) {
        lastUpdate = System.currentTimeMillis();
        if (audio.length > 0) {
            targetVolume = AudioUtils.getSpeechVolume(audio);
        } else {
            targetVolume = 0F;
        }
    }

    public float getHeadYScale() {
        float deltaTicks = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);

        if (deltaTicks > 2F) {
            deltaTicks = 2F;
        }

        float smoothingFactor = 0.75F;
        float blend = 1F - (float) Math.pow(1F - smoothingFactor, deltaTicks);

        if (targetVolume != 0F && System.currentTimeMillis() - lastUpdate > 200L) {
            targetVolume = 0F;
        }

        currentRenderScale = Mth.lerp(blend, currentRenderScale, targetVolume);

        return 1F + currentRenderScale * 0.5F;
    }

}
