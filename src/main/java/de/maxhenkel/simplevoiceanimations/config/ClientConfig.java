package de.maxhenkel.simplevoiceanimations.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.EnumConfigEntry;

public class ClientConfig {

    public final EnumConfigEntry<HeadAnimationStyle> headAnimationStyle;

    public ClientConfig(ConfigBuilder builder) {
        headAnimationStyle = builder.enumEntry("head_animation_style", HeadAnimationStyle.SCALE_Y);
    }

    public enum HeadAnimationStyle {
        NONE,
        SCALE_Y,
        SCALE_FULL
    }

}
