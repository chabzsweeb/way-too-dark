package studio.serenity.waytoodark;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
public final class SuffocationState {
    public static final long  NIGHT_START     = 13_000L;
    public static final long  NIGHT_END       = 23_000L;
    public static final float BASE_FOG_RADIUS = 2.0F;
    private static volatile float  dynamicLightBoost = 0.0F;
    private static volatile double savedGamma        = 0.5;
    private SuffocationState() {}
    public static boolean shouldApplyDarkness() {
        final Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return false;
        if (!Level.OVERWORLD.equals(mc.level.dimension())) return false;
        final long dayTime = mc.level.getDayTime() % 24_000L;
        if (dayTime >= NIGHT_START && dayTime <= NIGHT_END) return true;
        final Vec3 eye = mc.player.getEyePosition();
        final BlockPos eyePos = new BlockPos(Mth.floor(eye.x), Mth.floor(eye.y), Mth.floor(eye.z));
        return mc.level.getBrightness(LightLayer.SKY, eyePos) == 0;
    }
    public static float getEffectiveFogRadius() {
        return BASE_FOG_RADIUS + Math.max(0.0F, dynamicLightBoost);
    }
    public static void setDynamicLightBoost(float blocks) {
        dynamicLightBoost = Math.max(0.0F, blocks);
    }
    public static void saveThenZeroGamma(Minecraft mc) {
        savedGamma = mc.options.gamma;
        mc.options.gamma = 0.0;
    }
    public static void restoreGamma(Minecraft mc) {
        mc.options.gamma = savedGamma;
    }
}
