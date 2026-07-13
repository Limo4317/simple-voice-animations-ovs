package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimationsClient;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class IntensitySliderWidget extends AbstractSliderButton {

    private static final float STEP = 0.05F;

    public IntensitySliderWidget(int x, int y, int width, int height) {
        super(x, y, width, height, CommonComponents.EMPTY, toSliderValue(SimpleVoiceAnimationsClient.CLIENT_CONFIG.getIntensity()));
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.translatable("gui.simplevoiceanimations.settings.intensity", Math.round(getIntensity() * 100F)));
    }

    @Override
    protected void applyValue() {
        float intensity = getIntensity();
        if (intensity != SimpleVoiceAnimationsClient.CLIENT_CONFIG.getIntensity()) {
            SimpleVoiceAnimationsClient.CLIENT_CONFIG.setIntensity(intensity);
        }
    }

    private float getIntensity() {
        float intensity = Preferences.MIN_INTENSITY + (float) value * (Preferences.MAX_INTENSITY - Preferences.MIN_INTENSITY);
        return Math.round(intensity / STEP) * STEP;
    }

    private static double toSliderValue(float intensity) {
        return (intensity - Preferences.MIN_INTENSITY) / (Preferences.MAX_INTENSITY - Preferences.MIN_INTENSITY);
    }

}
