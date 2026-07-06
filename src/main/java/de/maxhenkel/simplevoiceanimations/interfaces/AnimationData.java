package de.maxhenkel.simplevoiceanimations.interfaces;

import de.maxhenkel.simplevoiceanimations.utils.AudioUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class AnimationData {

    public float targetVolume;
    private float currentRenderScale = 1F;

    public void addVoiceData(short[] audio) {
        this.targetVolume = AudioUtils.getSpeechVolume(audio);
    }

    public float getHeadYScale() {
        float deltaTicks = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);

        if (deltaTicks > 2F) {
            deltaTicks = 2F;
        }

        float smoothingFactor = 0.75F;
        float blend = 1F - (float) Math.pow(1F - smoothingFactor, deltaTicks);

        currentRenderScale = Mth.lerp(blend, currentRenderScale, targetVolume);

        return 1F + currentRenderScale * 0.5F;
    }

}
