package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

public class Preferences {

    public static final float MIN_SPLIT_HEIGHT = 0.5F;
    public static final float MAX_SPLIT_HEIGHT = 7.5F;
    public static final float MIN_INTENSITY = 0F;
    public static final float MAX_INTENSITY = 2F;
    public static final float DEFAULT_INTENSITY = 1F;

    private ClientConfig.HeadAnimationStyle headAnimationStyle;
    private float headSplitHeight;
    private float intensity;

    public Preferences(ClientConfig.HeadAnimationStyle headAnimationStyle, float headSplitHeight, float intensity) {
        this.headAnimationStyle = headAnimationStyle;
        this.headSplitHeight = headSplitHeight;
        this.intensity = intensity;
    }

    public ClientConfig.HeadAnimationStyle getHeadAnimationStyle() {
        return headAnimationStyle;
    }

    public void setHeadAnimationStyle(ClientConfig.HeadAnimationStyle headAnimationStyle) {
        this.headAnimationStyle = headAnimationStyle;
    }

    public float getHeadSplitHeight() {
        return headSplitHeight;
    }

    public void setHeadSplitHeight(float headSplitHeight) {
        this.headSplitHeight = headSplitHeight;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public static Preferences read(FriendlyByteBuf buffer) {
        ClientConfig.HeadAnimationStyle headAnimationStyle = buffer.readEnum(ClientConfig.HeadAnimationStyle.class);
        float headSplitHeight = buffer.readFloat();
        float intensity = buffer.readFloat();
        return new Preferences(
                headAnimationStyle,
                clamp(headSplitHeight, MIN_SPLIT_HEIGHT, MAX_SPLIT_HEIGHT, MIN_SPLIT_HEIGHT),
                clamp(intensity, MIN_INTENSITY, MAX_INTENSITY, DEFAULT_INTENSITY)
        );
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeEnum(headAnimationStyle);
        buffer.writeFloat(headSplitHeight);
        buffer.writeFloat(intensity);
    }

    private static float clamp(float value, float min, float max, float fallback) {
        if (!Float.isFinite(value)) {
            return fallback;
        }
        return Mth.clamp(value, min, max);
    }

}
