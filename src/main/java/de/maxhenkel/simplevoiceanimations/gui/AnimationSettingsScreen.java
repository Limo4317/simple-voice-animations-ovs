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

    private static final int FACE_SIZE = 64;

    private SplitHeightWidget splitHeightWidget;

    public AnimationSettingsScreen() {
        super(Component.translatable("gui.simplevoiceanimations.settings.title"));
    }

    @Override
    protected void init() {
        addRenderableWidget(CycleButton.builder(AnimationSettingsScreen::getStyleName, SimpleVoiceAnimationsClient.CLIENT_CONFIG.getHeadAnimationStyle())
                .withValues(ClientConfig.HeadAnimationStyle.values())
                .create(width / 2 - 125, 26, 250, 20, Component.translatable("gui.simplevoiceanimations.settings.style"), (button, style) -> {
                    SimpleVoiceAnimationsClient.CLIENT_CONFIG.setHeadAnimationStyle(style);
                    splitHeightWidget.visible = style.hasSplit();
                }));
        addRenderableWidget(new PlayerPreviewWidget(width / 2 - 30, 48, 60, 80, 30F));
        splitHeightWidget = new SplitHeightWidget(0, 144, FACE_SIZE);
        splitHeightWidget.setX(width / 2 - splitHeightWidget.getWidth() / 2);
        splitHeightWidget.visible = SimpleVoiceAnimationsClient.CLIENT_CONFIG.getHeadAnimationStyle().hasSplit();
        addRenderableWidget(splitHeightWidget);
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).bounds(width / 2 - 75, height - 28, 150, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.centeredText(font, title, width / 2, 12, -1);
        if (splitHeightWidget.visible) {
            graphics.centeredText(font, Component.translatable("gui.simplevoiceanimations.settings.split_height.description"), width / 2, splitHeightWidget.getY() - 12, 0xFFAAAAAA);
        }
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
