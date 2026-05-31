package studio.serenity.waytoodark.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.serenity.waytoodark.DynamicLightAdapter;
import studio.serenity.waytoodark.SuffocationState;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(method = "method_24873", at = @At("RETURN"), remap = false)
    private static void wayTooDark$overrideFog(
            Camera camera,
            Object fogMode,
            float viewDistance,
            int i,
            int j,
            int k,
            CallbackInfoReturnable<Float> cir) {
        if (!SuffocationState.shouldApplyDarkness()) return;
        SuffocationState.setDynamicLightBoost(DynamicLightAdapter.getDynamicLightRadius());
        RenderSystem.setShaderFogStart(0.0F);
        RenderSystem.setShaderFogEnd(SuffocationState.getEffectiveFogRadius());
    }
}
