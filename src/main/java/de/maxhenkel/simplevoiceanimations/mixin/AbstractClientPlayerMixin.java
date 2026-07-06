package de.maxhenkel.simplevoiceanimations.mixin;

import de.maxhenkel.simplevoiceanimations.interfaces.AnimationDataExtension;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin implements AnimationDataExtension {

    @Unique
    private float speechVolume;

    @Override
    public float simplevoiceanimations$getSpeechVolume() {
        return speechVolume;
    }

    @Override
    public void simplevoiceanimations$setSpeechVolume(float speechVolume) {
        this.speechVolume = speechVolume;
    }

}
