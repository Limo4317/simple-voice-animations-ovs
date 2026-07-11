package de.maxhenkel.simplevoiceanimations.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.EnumConfigEntry;
import de.maxhenkel.configbuilder.entry.FloatConfigEntry;

public class ClientConfig {

    public final EnumConfigEntry<HeadAnimationStyle> headAnimationStyle;
    public final FloatConfigEntry headSplitHeight;

    public ClientConfig(ConfigBuilder builder) {
        headAnimationStyle = builder.enumEntry("head_animation_style", HeadAnimationStyle.SCALE_Y);
        headSplitHeight = builder.floatEntry("head_split_height", 2F, 0.5F, 7.5F,
                "The height of the head split in pixels, measured from the bottom of the head"
        );
    }

    public enum HeadAnimationStyle {
        NONE,
        SCALE_Y,
        SCALE_FULL,
        SPLIT
    }

}
