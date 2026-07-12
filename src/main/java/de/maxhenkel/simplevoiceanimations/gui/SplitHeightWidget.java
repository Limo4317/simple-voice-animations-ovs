package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimationsClient;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.PlayerFaceExtractor;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class SplitHeightWidget extends AbstractWidget {

    private static final int BAR_OVERHANG = 8;
    private static final float HEAD_PIXELS = 8F;

    public SplitHeightWidget(int x, int y, int faceSize) {
        super(x, y, faceSize + BAR_OVERHANG * 2, faceSize, Component.translatable("gui.simplevoiceanimations.settings.split_height"));
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        PlayerFaceExtractor.extractRenderState(graphics, player.getSkin(), getX() + BAR_OVERHANG, getY(), getHeight());
        int barY = getY() + Math.round(getHeight() - SimpleVoiceAnimationsClient.CLIENT_CONFIG.getHeadSplitHeight() / HEAD_PIXELS * getHeight());
        graphics.fill(getX(), barY - 1, getRight(), barY + 1, isHoveredOrFocused() ? 0xFFFFFFFF : 0xFFAAAAAA);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        setSplitHeightFromMouse(event.y());
    }

    @Override
    protected void onDrag(MouseButtonEvent event, double dx, double dy) {
        setSplitHeightFromMouse(event.y());
    }

    private void setSplitHeightFromMouse(double mouseY) {
        float splitHeight = (float) (getY() + getHeight() - mouseY) / getHeight() * HEAD_PIXELS;
        splitHeight = Math.round(splitHeight * 2F) / 2F;
        SimpleVoiceAnimationsClient.CLIENT_CONFIG.setHeadSplitHeight(Mth.clamp(splitHeight, Preferences.MIN_SPLIT_HEIGHT, Preferences.MAX_SPLIT_HEIGHT));
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }

}
