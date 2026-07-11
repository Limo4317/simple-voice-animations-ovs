package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.SplitHeadModel;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderExtension;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderState;
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
        if (!head.hasChild(SplitHeadModel.HEAD_TOP)) {
            return;
        }
        ModelPart headTop = head.getChild(SplitHeadModel.HEAD_TOP);
        ModelPart headBottom = head.getChild(SplitHeadModel.HEAD_BOTTOM);
        head.skipDraw = state.splitHead;
        headTop.visible = state.splitHead;
        headBottom.visible = state.splitHead;
        if (state.splitHead) {
            headTop.getChild(SplitHeadModel.HAT_TOP).visible = hat.visible;
            headBottom.getChild(SplitHeadModel.HAT_BOTTOM).visible = hat.visible;
            hat.visible = false;
            headTop.xRot = -state.headSplitRotation;
        }
    }

}
