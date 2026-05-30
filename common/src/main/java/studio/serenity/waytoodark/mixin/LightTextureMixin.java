package studio.serenity.waytoodark.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.serenity.waytoodark.SuffocationState;
@Mixin(LightTexture.class)
public abstract class LightTextureMixin {
    @Shadow private Minecraft minecraft;
    private boolean wayTooDark$gammaHijacked = false;
    @Inject(method = "updateLightTexture", at = @At("HEAD"), remap = true)
    private void wayTooDark$preUpdate(float partialTick, CallbackInfo ci) {
        if (!SuffocationState.shouldApplyDarkness()) return;
        SuffocationState.saveThenZeroGamma(this.minecraft);
        this.wayTooDark$gammaHijacked = true;
    }
    @Inject(method = "updateLightTexture", at = @At("RETURN"), remap = true)
    private void wayTooDark$postUpdate(float partialTick, CallbackInfo ci) {
        if (this.wayTooDark$gammaHijacked) {
            SuffocationState.restoreGamma(this.minecraft);
            this.wayTooDark$gammaHijacked = false;
        }
    }
}
