package de.maxhenkel.simplevoiceanimations.gui;

import de.maxhenkel.simplevoiceanimations.RenderStateExtractor;
import de.maxhenkel.simplevoiceanimations.SimpleVoiceAnimationsClient;
import de.maxhenkel.simplevoiceanimations.interfaces.AnimationRenderExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class PlayerPreviewWidget extends AbstractWidget {

    private final float scale;

    public PlayerPreviewWidget(int x, int y, int width, int height, float scale) {
        super(x, y, width, height, CommonComponents.EMPTY);
        this.scale = scale;
        active = false;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        EntityRenderState renderState = extractPlayerRenderState(player);
        float centerX = (getX() + getRight()) / 2F;
        float centerY = (getY() + getBottom()) / 2F;
        float xAngle = (float) Math.atan((centerX - mouseX) / 40F);
        float yAngle = (float) Math.atan((centerY - mouseY) / 40F);
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf cameraAngle = new Quaternionf().rotateX(yAngle * 20F * Mth.DEG_TO_RAD);
        rotation.mul(cameraAngle);
        if (renderState instanceof LivingEntityRenderState livingState) {
            livingState.bodyRot = 180F + xAngle * 20F;
            livingState.yRot = xAngle * 20F;
            livingState.xRot = livingState.pose == Pose.FALL_FLYING ? 0F : -yAngle * 20F;
            livingState.boundingBoxWidth = livingState.boundingBoxWidth / livingState.scale;
            livingState.boundingBoxHeight = livingState.boundingBoxHeight / livingState.scale;
            livingState.scale = 1F;
        }
        Vector3f translation = new Vector3f(0F, renderState.boundingBoxHeight / 2F, 0F);
        graphics.entity(renderState, scale, translation, rotation, cameraAngle, getX(), getY(), getRight(), getBottom());
    }

    private static EntityRenderState extractPlayerRenderState(LocalPlayer player) {
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LocalPlayer, ?> renderer = dispatcher.getRenderer(player);
        EntityRenderState renderState = renderer.createRenderState(player, 1F);
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;
        if (renderState instanceof AnimationRenderExtension animationExtension) {
            RenderStateExtractor.applyStyle(animationExtension.simplevoiceanimations$getState(), SimpleVoiceAnimationsClient.CLIENT_CONFIG.getLocalPreferences(), getPreviewProgress());
        }
        return renderState;
    }

    private static float getPreviewProgress() {
        return (float) Math.sin(System.currentTimeMillis() / 250D) * 0.5F + 0.5F;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
    }

    @Nullable
    @Override
    public ComponentPath nextFocusPath(FocusNavigationEvent navigationEvent) {
        return null;
    }

}
