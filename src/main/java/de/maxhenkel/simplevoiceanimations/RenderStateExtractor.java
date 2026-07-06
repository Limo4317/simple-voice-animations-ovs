package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationData;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderState;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;

public class RenderStateExtractor {

    public static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractRenderState(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationDataExtension entityExtension, AnimationRenderExtension stateExtension) {
        AnimationRenderState renderState = stateExtension.simplevoiceanimations$getState();
        AnimationData animationData = entityExtension.simplevoiceanimations$getData();
        renderState.headYScale = 1F;
        ClientConfig.HeadAnimationStyle headAnimationStyle = SimpleVoiceAnimations.CLIENT_CONFIG.headAnimationStyle.get();
        switch (headAnimationStyle) {
            case SCALE_Y:
                extractYScale(entity, state, partialTicks, animationData, renderState);
                break;
        }
    }

    private static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractYScale(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationData data, AnimationRenderState renderState) {
        renderState.headYScale = data.getHeadYScale();
    }

}
