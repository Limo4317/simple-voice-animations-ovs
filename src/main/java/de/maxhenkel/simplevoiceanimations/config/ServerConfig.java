package de.maxhenkel.simplevoiceanimations.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.EnumConfigEntry;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;

import java.util.Arrays;

public class ServerConfig {

    private final EnumConfigEntry<ClientConfig.HeadAnimationStyle> defaultHeadAnimationStyle;

    public ServerConfig(ConfigBuilder builder) {
        defaultHeadAnimationStyle = builder.enumEntry("default_head_animation_style", ClientConfig.HeadAnimationStyle.NONE,
                "The head animation style used for players that don't have the mod installed on their client",
                "Possible values are: %s".formatted(String.join(", ", Arrays.stream(ClientConfig.HeadAnimationStyle.values()).map(Enum::name).toList()))
        );
    }

    public Preferences getDefaultPreferences() {
        return new Preferences(defaultHeadAnimationStyle.get(), Preferences.DEFAULT_SPLIT_HEIGHT, Preferences.DEFAULT_INTENSITY);
    }

}
