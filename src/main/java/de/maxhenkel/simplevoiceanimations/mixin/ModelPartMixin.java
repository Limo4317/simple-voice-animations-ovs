package de.maxhenkel.simplevoiceanimations.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.maxhenkel.simplevoiceanimations.SplitHeadRenderer;
import de.maxhenkel.simplevoiceanimations.interfaces.SplitHeadExtension;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ModelPart.class)
public class ModelPartMixin implements SplitHeadExtension {

    @Shadow
    @Final
    private List<ModelPart.Cube> cubes;

    @Unique
    private boolean splitHead;
    @Unique
    private float splitHeight;
    @Unique
    private float splitRotation;
    @Unique
    private float splitDistance;

    @Override
    public void simplevoiceanimations$setSplitHead(boolean split, float height, float rotation, float distance) {
        splitHead = split;
        splitHeight = height;
        splitRotation = rotation;
        splitDistance = distance;
    }

    @Inject(method = "compile", at = @At("HEAD"), cancellable = true)
    private void compile(PoseStack.Pose pose, VertexConsumer buffer, int lightCoords, int overlayCoords, int color, CallbackInfo ci) {
        if (!splitHead) {
            return;
        }
        ci.cancel();
        SplitHeadRenderer.render(cubes, pose, buffer, lightCoords, overlayCoords, color, splitHeight, splitRotation, splitDistance);
    }

}
