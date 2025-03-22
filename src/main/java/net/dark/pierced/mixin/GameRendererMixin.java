package net.dark.pierced.mixin;

import net.dark.pierced.item.custom.LongCrossbow;
import net.dark.pierced.zoomlyhandling;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.render.GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void zoomifyFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if(client.player != null && zoomlyhandling.isZooming()) {
            double baseFov = cir.getReturnValue();
            double zoomFactor = zoomlyhandling.getZoomFactor();
            cir.setReturnValue(baseFov * zoomFactor);
        }
    }
}