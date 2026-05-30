package studio.serenity.waytoodark;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import java.lang.reflect.Method;
public final class DynamicLightAdapter {
    private static final boolean LAMBDYNLIGHTS_PRESENT = isModPresent("lambdynlights");
    private DynamicLightAdapter() {}
    public static float getDynamicLightRadius() {
        final Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0.0F;
        if (LAMBDYNLIGHTS_PRESENT) {
            final float r = queryLambDynLights(mc.player);
            if (r >= 0.0F) return r;
        }
        return heldBlockLightRadius(mc.player);
    }
    private static float queryLambDynLights(Player player) {
        try {
            final Class<?> h = Class.forName("dev.lambdaurora.lambdynlights.api.DynamicLightHandlers");
            final Method m = h.getDeclaredMethod("getLuminanceOf", net.minecraft.world.entity.LivingEntity.class);
            final int lum = (int) m.invoke(null, player);
            return lum > 0 ? lum * 0.75F : 0.0F;
        } catch (Exception ignored) { return -1.0F; }
    }
    private static float heldBlockLightRadius(Player player) {
        int max = 0;
        for (ItemStack s : new ItemStack[]{player.getMainHandItem(), player.getOffhandItem()}) {
            if (s.isEmpty()) continue;
            if (s.getItem() instanceof BlockItem bi) {
                int e = bi.getBlock().defaultBlockState().getLightEmission();
                if (e > max) max = e;
            }
        }
        return max > 0 ? max * 0.75F : 0.0F;
    }
    private static boolean isModPresent(String modId) {
        try {
            Class<?> p = Class.forName("dev.architectury.platform.Platform");
            return (boolean) p.getMethod("isModLoaded", String.class).invoke(null, modId);
        } catch (Exception ignored) {}
        try { Class.forName("dev.lambdaurora.lambdynlights.LambDynLights"); return true; }
        catch (ClassNotFoundException ignored) { return false; }
    }
}
