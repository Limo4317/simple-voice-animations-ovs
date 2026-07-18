package de.maxhenkel.simplevoiceanimations.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.PlayerSkin;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SkinUtils {

    @Nullable
    private static Supplier<PlayerSkin> skinLookup;

    public static PlayerSkin getLocalPlayerSkin() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            return player.getSkin();
        }
        if (skinLookup == null) {
            skinLookup = mc.getSkinManager().createLookup(mc.getGameProfile(), false);
        }
        return skinLookup.get();
    }

}
