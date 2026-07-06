package de.maxhenkel.simplevoiceanimations.utils;

public class AudioUtils {

    private static final float MIN_DB = -60F;

    public static float getSpeechVolume(short[] audio) {
        if (audio == null || audio.length <= 0) {
            return 0F;
        }

        long sumOfSquares = 0L;
        for (short sample : audio) {
            sumOfSquares += (long) sample * sample;
        }

        if (sumOfSquares <= 0L) {
            return 0F;
        }

        float rms = (float) Math.sqrt((double) sumOfSquares / audio.length);
        float dbfs = 20F * (float) Math.log10(rms / Short.MAX_VALUE);
        float perceptualVolume = 1F + (dbfs / Math.abs(MIN_DB));
        return Math.clamp(perceptualVolume, 0F, 1F);
    }

}
