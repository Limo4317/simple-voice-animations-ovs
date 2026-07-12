package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationData;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderState;
import de.maxhenkel.simplevoiceanimations.sync.ClientPreferencesSync;
import de.maxhenkel.simplevoiceanimations.sync.Preferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;

public class RenderStateExtractor {

    private static final float MAX_HEAD_SPLIT_ROTATION = Mth.DEG_TO_RAD * 60F;

    public static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractRenderState(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationDataExtension entityExtension, AnimationRenderExtension stateExtension) {
        AnimationRenderState renderState = stateExtension.simplevoiceanimations$getState();
        AnimationData animationData = entityExtension.simplevoiceanimations$getData();
        renderState.headXScale = 1F;
        renderState.headYScale = 1F;
        renderState.headZScale = 1F;
        renderState.splitHead = false;
        renderState.headSplitRotation = 0F;
        Preferences preferences = getPreferences(entity);
        switch (preferences.getHeadAnimationStyle()) {
            case SCALE_Y:
                extractYScale(entity, state, partialTicks, animationData, renderState);
                break;
            case SCALE_FULL:
                extractFullScale(entity, state, partialTicks, animationData, renderState);
                break;
            case SPLIT:
                extractSplit(entity, state, partialTicks, animationData, renderState, preferences);
                break;
        }
    }

    private static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> Preferences getPreferences(AvatarlikeEntity entity) {
        if (entity != Minecraft.getInstance().player) {
            Preferences synced = ClientPreferencesSync.getPreferences(entity.getUUID());
            if (synced != null) {
                return synced;
            }
        }
        return SimpleVoiceAnimationsClient.CLIENT_CONFIG.getLocalPreferences();
    }

    private static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractYScale(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationData data, AnimationRenderState renderState) {
        renderState.headYScale = 1F + data.getAnimationProgress() * 0.5F;
    }

    private static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractFullScale(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationData data, AnimationRenderState renderState) {
        float scale = 1F + data.getAnimationProgress() * 0.5F;
        renderState.headXScale = scale;
        renderState.headYScale = scale;
        renderState.headZScale = scale;
    }

    private static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractSplit(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationData data, AnimationRenderState renderState, Preferences preferences) {
        renderState.splitHead = true;
        renderState.headSplitHeight = preferences.getHeadSplitHeight();
        renderState.headSplitRotation = data.getAnimationProgress() * MAX_HEAD_SPLIT_ROTATION;
    }

}
