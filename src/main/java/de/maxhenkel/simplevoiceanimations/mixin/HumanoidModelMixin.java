package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderState;
import de.maxhenkel.simplevoiceanimations.interfaces.SplitHeadExtension;
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

    @Shadow
    @Final
    public ModelPart hat;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("RETURN"))
    public void setupAnim(HumanoidRenderState s, CallbackInfo ci) {
        if (!(s instanceof AnimationRenderExtension stateExtension)) {
            return;
        }
        AnimationRenderState state = stateExtension.simplevoiceanimations$getState();
        head.xScale = state.headXScale;
        head.yScale = state.headYScale;
        head.zScale = state.headZScale;
        ((SplitHeadExtension) (Object) head).simplevoiceanimations$setSplitHead(state.splitHead, state.headSplitHeight, state.headSplitRotation);
        ((SplitHeadExtension) (Object) hat).simplevoiceanimations$setSplitHead(state.splitHead, state.headSplitHeight, state.headSplitRotation);
    }

}
