package de.maxhenkel.simplevoiceanimations;

import de.maxhenkel.simplevoiceanimations.config.ClientConfig;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderDataExtension;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;

public class RenderStateExtractor {

    public static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractRenderState(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationDataExtension entityExtension, AnimationRenderDataExtension stateExtension) {
        stateExtension.simplevoiceanimations$setHeadYScale(1F);
        ClientConfig.HeadAnimationStyle headAnimationStyle = SimpleVoiceAnimations.CLIENT_CONFIG.headAnimationStyle.get();
        switch (headAnimationStyle) {
            case SCALE_Y:
                extractYScale(entity, state, partialTicks, entityExtension, stateExtension);
                break;
        }
    }

    private static <AvatarlikeEntity extends Avatar & ClientAvatarEntity> void extractYScale(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, AnimationDataExtension entityExtension, AnimationRenderDataExtension stateExtension) {
        stateExtension.simplevoiceanimations$setHeadYScale(1F + entityExtension.simplevoiceanimations$getSpeechVolume() * 0.5F);
    }

}
