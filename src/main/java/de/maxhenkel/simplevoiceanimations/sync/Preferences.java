package de.maxhenkel.simplevoiceanimations.sync;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

public class Preferences {

    public static final float MIN_SPLIT_HEIGHT = 0.5F;
    public static final float MAX_SPLIT_HEIGHT = 7.5F;

    private ClientConfig.HeadAnimationStyle headAnimationStyle;
    private float headSplitHeight;

    public Preferences(ClientConfig.HeadAnimationStyle headAnimationStyle, float headSplitHeight) {
        this.headAnimationStyle = headAnimationStyle;
        this.headSplitHeight = headSplitHeight;
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

    public static Preferences read(FriendlyByteBuf buffer) {
        ClientConfig.HeadAnimationStyle headAnimationStyle = buffer.readEnum(ClientConfig.HeadAnimationStyle.class);
        float headSplitHeight = buffer.readFloat();
        if (!Float.isFinite(headSplitHeight)) {
            headSplitHeight = MIN_SPLIT_HEIGHT;
        }
        return new Preferences(headAnimationStyle, Mth.clamp(headSplitHeight, MIN_SPLIT_HEIGHT, MAX_SPLIT_HEIGHT));
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeEnum(headAnimationStyle);
        buffer.writeFloat(headSplitHeight);
    }

}
