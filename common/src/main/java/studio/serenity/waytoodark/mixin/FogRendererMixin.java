package studio.serenity.waytoodark.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.serenity.waytoodark.DynamicLightAdapter;
import studio.serenity.waytoodark.SuffocationState;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(method = "method_24873", at = @At("HEAD"), remap = false)
    private static void wayTooDark$sampleLight(Camera camera, FogRenderer.FogMode fogMode,
            float farPlaneDistance, boolean isFoggy, float partialTick, CallbackInfo ci) {
        SuffocationState.setDynamicLightBoost(DynamicLightAdapter.getDynamicLightRadius());
    }

    @Inject(method = "method_24873", at = @At("RETURN"), remap = false)
    private static void wayTooDark$overrideFog(Camera camera, FogRenderer.FogMode fogMode,
            float farPlaneDistance, boolean isFoggy, float partialTick, CallbackInfo ci) {
        if (!SuffocationState.shouldApplyDarkness()) return;
        RenderSystem.setShaderFogStart(0.0F);
        RenderSystem.setShaderFogEnd(SuffocationState.getEffectiveFogRadius());
    }
}
