package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderState;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;

public class RenderStateExtractor {

    private static final float MAX_HEAD_SPLIT_ROTATION = Mth.DEG_TO_RAD * 60F;

    public static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractRenderState(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationDataExtension entityExtension, AnimationRenderExtension stateExtension) {
        Preferences preferences = ClientPreferencesSync.getPreferences(entity.getUUID());
        applyStyle(stateExtension.simplevoiceanimations$getState(), preferences, entityExtension.simplevoiceanimations$getData().getAnimationProgress());
    }

    public static void applyStyle(AnimationRenderState renderState, Preferences preferences, float animationProgress) {
        renderState.headXScale = 1F;
        renderState.headYScale = 1F;
        renderState.headZScale = 1F;
        renderState.splitHead = false;
        renderState.headSplitRotation = 0F;
        switch (preferences.getHeadAnimationStyle()) {
            case SCALE_Y:
                renderState.headYScale = 1F + animationProgress * 0.5F;
                break;
            case SCALE_FULL:
                float scale = 1F + animationProgress * 0.5F;
                renderState.headXScale = scale;
                renderState.headYScale = scale;
                renderState.headZScale = scale;
                break;
            case SPLIT:
                renderState.splitHead = true;
                renderState.headSplitHeight = preferences.getHeadSplitHeight();
                renderState.headSplitRotation = animationProgress * MAX_HEAD_SPLIT_ROTATION;
                break;
        }
    }

}
