package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderDataExtension;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {

    @Shadow
    @Final
    public ModelPart head;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("RETURN"))
    public void setupAnim(HumanoidRenderState state, CallbackInfo ci) {
        if (!(state instanceof AnimationRenderDataExtension stateExtension)) {
            return;
        }
        head.yScale = stateExtension.simplevoiceanimations$getHeadYScale();
    }

}
