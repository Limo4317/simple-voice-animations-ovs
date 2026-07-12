package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimationsClient;
import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public class AnimationSettingsScreen extends Screen {

    private static final int FACE_SIZE = 56;

    private SplitHeightWidget splitHeightWidget;

    public AnimationSettingsScreen() {
        super(Component.translatable("gui.simplevoiceanimations.settings.title"));
    }

    @Override
    protected void init() {
        Preferences preferences = SimpleVoiceAnimationsClient.CLIENT_CONFIG.getLocalPreferences();
        addRenderableWidget(CycleButton.builder(AnimationSettingsScreen::getStyleName, preferences.getHeadAnimationStyle())
                .withValues(ClientConfig.HeadAnimationStyle.values())
                .create(width / 2 - 75, 30, 150, 20, Component.translatable("gui.simplevoiceanimations.settings.style"), (button, style) -> {
                    preferences.setHeadAnimationStyle(style);
                    splitHeightWidget.visible = style == ClientConfig.HeadAnimationStyle.SPLIT;
                }));
        addRenderableWidget(new PlayerPreviewWidget(width / 2 - 30, 52, 60, 80, 30F));
        splitHeightWidget = new SplitHeightWidget(0, 140, FACE_SIZE, preferences);
        splitHeightWidget.setX(width / 2 - splitHeightWidget.getWidth() / 2);
        splitHeightWidget.visible = preferences.getHeadAnimationStyle() == ClientConfig.HeadAnimationStyle.SPLIT;
        addRenderableWidget(splitHeightWidget);
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).bounds(width / 2 - 75, height - 28, 150, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.centeredText(font, title, width / 2, 15, -1);
    }

    @Override
    public void onClose() {
        Preferences preferences = SimpleVoiceAnimationsClient.CLIENT_CONFIG.getLocalPreferences();
        SimpleVoiceAnimationsClient.CLIENT_CONFIG.setHeadAnimationStyle(preferences.getHeadAnimationStyle());
        SimpleVoiceAnimationsClient.CLIENT_CONFIG.setHeadSplitHeight(preferences.getHeadSplitHeight());
        super.onClose();
    }

    private static Component getStyleName(ClientConfig.HeadAnimationStyle style) {
        return Component.translatable("gui.simplevoiceanimations.settings.style." + style.name().toLowerCase(Locale.ROOT));
    }

}
