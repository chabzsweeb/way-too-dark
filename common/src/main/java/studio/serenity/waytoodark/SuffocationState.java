package studio.serenity.waytoodark;

import net.minecraft.client.Minecraft;

public final class SuffocationState {
    public static final float BASE_FOG_RADIUS = 2.0F;
    private static volatile float dynamicLightBoost = 0.0F;

    private SuffocationState() {}

    public static boolean shouldApplyDarkness() {
        return true;
    }

    public static float getEffectiveFogRadius() {
        return BASE_FOG_RADIUS + Math.max(0.0F, dynamicLightBoost);
    }

    public static void setDynamicLightBoost(float blocks) {
        dynamicLightBoost = Math.max(0.0F, blocks);
    }

    public static void saveThenZeroGamma(Minecraft mc) {}
    public static void restoreGamma(Minecraft mc) {}
}
