package studio.serenity.waytoodark.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.serenity.waytoodark.DynamicLightAdapter;
import studio.serenity.waytoodark.SuffocationState;

@Mixin(targets = "me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer", remap = false)
public class FogRendererMixin {

    @Inject(method = "drawChunkLayer", at = @At("HEAD"), remap = false)
    private void wayTooDark$overrideFog(CallbackInfo ci) {
        if (!SuffocationState.shouldApplyDarkness()) return;
        SuffocationState.setDynamicLightBoost(DynamicLightAdapter.getDynamicLightRadius());
        RenderSystem.setShaderFogStart(0.0F);
        RenderSystem.setShaderFogEnd(SuffocationState.getEffectiveFogRadius());
    }
}
