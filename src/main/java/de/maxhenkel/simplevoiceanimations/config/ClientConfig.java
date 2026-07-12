package de.maxhenkel.simplevoiceanimations.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.EnumConfigEntry;
import de.maxhenkel.configbuilder.entry.FloatConfigEntry;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;

import javax.annotation.Nullable;

public class ClientConfig {

    private final EnumConfigEntry<HeadAnimationStyle> headAnimationStyle;
    private final FloatConfigEntry headSplitHeight;

    @Nullable
    private Preferences localPreferences;

    public ClientConfig(ConfigBuilder builder) {
        headAnimationStyle = builder.enumEntry("head_animation_style", HeadAnimationStyle.SCALE_Y);
        headSplitHeight = builder.floatEntry("head_split_height", 2F, Preferences.MIN_SPLIT_HEIGHT, Preferences.MAX_SPLIT_HEIGHT,
                "The height of the head split in pixels, measured from the bottom of the head"
        );
    }

    public HeadAnimationStyle getHeadAnimationStyle() {
        return headAnimationStyle.get();
    }

    public void setHeadAnimationStyle(HeadAnimationStyle style) {
        headAnimationStyle.set(style).save();
        getLocalPreferences().setHeadAnimationStyle(headAnimationStyle.get());
        ClientPreferencesSync.sendLocalPreferences();
    }

    public float getHeadSplitHeight() {
        return headSplitHeight.get();
    }

    public void setHeadSplitHeight(float splitHeight) {
        headSplitHeight.set(splitHeight).save();
        getLocalPreferences().setHeadSplitHeight(headSplitHeight.get());
        ClientPreferencesSync.sendLocalPreferences();
    }

    public Preferences getLocalPreferences() {
        if (localPreferences == null) {
            localPreferences = new Preferences(headAnimationStyle.get(), headSplitHeight.get());
        }
        return localPreferences;
    }

    public enum HeadAnimationStyle {
        NONE,
        SCALE_Y,
        SCALE_FULL,
        SPLIT
    }

}
