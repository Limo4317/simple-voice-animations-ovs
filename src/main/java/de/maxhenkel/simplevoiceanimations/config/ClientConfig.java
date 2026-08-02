package de.maxhenkel.simplevoiceanimations.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.BooleanConfigEntry;
import de.maxhenkel.configbuilder.entry.EnumConfigEntry;
import de.maxhenkel.configbuilder.entry.FloatConfigEntry;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;

import javax.annotation.Nullable;

public class ClientConfig {

    public static Preferences DEFAULT_PREFERENCES = new Preferences(HeadAnimationStyle.NONE, Preferences.DEFAULT_SPLIT_HEIGHT, Preferences.DEFAULT_INTENSITY);

    private final EnumConfigEntry<HeadAnimationStyle> headAnimationStyle;
    private final FloatConfigEntry headSplitHeight;
    private final FloatConfigEntry intensity;
    private final BooleanConfigEntry fallbackToOwnAnimations;
    private final BooleanConfigEntry setUp;

    @Nullable
    private Preferences localPreferences;

    public ClientConfig(ConfigBuilder builder) {
        headAnimationStyle = builder.enumEntry("head_animation_style", DEFAULT_PREFERENCES.getHeadAnimationStyle());
        headSplitHeight = builder.floatEntry("head_split_height", DEFAULT_PREFERENCES.getHeadSplitHeight(), Preferences.MIN_SPLIT_HEIGHT, Preferences.MAX_SPLIT_HEIGHT,
                "The height of the head split in pixels, measured from the bottom of the head"
        );
        intensity = builder.floatEntry("intensity", DEFAULT_PREFERENCES.getIntensity(), Preferences.MIN_INTENSITY, Preferences.MAX_INTENSITY,
                "How strongly the head animation reacts to the voice, where 1 is the default strength"
        );
        fallbackToOwnAnimations = builder.booleanEntry("fallback_to_own_animations", true,
                "If the server does not have the mod installed and thus can't sync animation settings, your own animation settings are applied to all other players"
        );
        setUp = builder.booleanEntry("set_up", false,
                "If the player has configured their head animation"
        );
    }

    public HeadAnimationStyle getHeadAnimationStyle() {
        return headAnimationStyle.get();
    }

    public void setHeadAnimationStyle(HeadAnimationStyle style) {
        headAnimationStyle.set(style).save();
        setSetUp(true);
        getLocalPreferences().setHeadAnimationStyle(headAnimationStyle.get());
        ClientPreferencesSync.sendLocalPreferences();
    }

    public boolean isSetUp() {
        return setUp.get();
    }

    public void setSetUp(boolean setUp) {
        this.setUp.set(setUp).save();
    }

    public float getHeadSplitHeight() {
        return headSplitHeight.get();
    }

    public void setHeadSplitHeight(float splitHeight) {
        headSplitHeight.set(splitHeight).save();
        getLocalPreferences().setHeadSplitHeight(headSplitHeight.get());
        ClientPreferencesSync.sendLocalPreferences();
    }

    public float getIntensity() {
        return intensity.get();
    }

    public boolean isFallbackToOwnAnimations() {
        return fallbackToOwnAnimations.get();
    }

    public void setIntensity(float intensityValue) {
        intensity.set(intensityValue).save();
        getLocalPreferences().setIntensity(intensity.get());
        ClientPreferencesSync.sendLocalPreferences();
    }

    public Preferences getLocalPreferences() {
        if (localPreferences == null) {
            localPreferences = new Preferences(headAnimationStyle.get(), headSplitHeight.get(), intensity.get());
        }
        return localPreferences;
    }

    public enum HeadAnimationStyle {
        NONE(false),
        SQUISH(false),
        SCALE_Y(false),
        SCALE_FULL(false),
        SPLIT_HINGE(true),
        SPLIT_VERTICAL(true),
        NOD(false);

        private final boolean hasSplit;

        HeadAnimationStyle(boolean hasSplit) {
            this.hasSplit = hasSplit;
        }

        public boolean hasSplit() {
            return hasSplit;
        }
    }

}
