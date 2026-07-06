package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderDataExtension;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements AnimationRenderDataExtension {

    @Unique
    private float headYScale;

    @Override
    public float simplevoiceanimations$getHeadYScale() {
        return headYScale;
    }

    @Override
    public void simplevoiceanimations$setHeadYScale(float headYScale) {
        this.headYScale = headYScale;
    }
}
