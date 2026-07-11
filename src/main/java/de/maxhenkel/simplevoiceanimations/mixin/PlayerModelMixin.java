package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.SplitHeadModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerModel.class)
public class PlayerModelMixin {

    @Inject(method = "createMesh(Lnet/minecraft/client/model/geom/builders/CubeDeformation;Z)Lnet/minecraft/client/model/geom/builders/MeshDefinition;", at = @At("RETURN"))
    private static void createMesh(CubeDeformation scale, boolean slim, CallbackInfoReturnable<MeshDefinition> cir) {
        SplitHeadModel.addSplitHeadParts(cir.getReturnValue().getRoot().getChild("head"), scale);
    }

    @Inject(method = "createArmorMeshSet(Lnet/minecraft/client/model/geom/builders/CubeDeformation;Lnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/renderer/entity/ArmorModelSet;", at = @At("RETURN"))
    private static void createArmorMeshSet(CubeDeformation innerDeformation, CubeDeformation outerDeformation, CallbackInfoReturnable<ArmorModelSet<MeshDefinition>> cir) {
        SplitHeadModel.addSplitHeadParts(cir.getReturnValue().head().getRoot().getChild("head"), outerDeformation);
    }

}
