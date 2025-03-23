package net.dark.pierced.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dark.pierced.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    Identifier texture = Identifier.of("pierced", "textures/gui/scope_overlay.png");

    @Shadow
    private float spyglassScale;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void renderCustomSpyglassOverlay(DrawContext context, float scale, CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity) (Object) client.player;

        if (player.isSneaking() && (player.getMainHandStack().isOf(ModItems.LONGCROSSBOW)||player.getOffHandStack().isOf(ModItems.LONGCROSSBOW))) {
            float f = (float) Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
            float h = Math.min((float) context.getScaledWindowWidth() / f, (float) context.getScaledWindowHeight() / f) * scale;
            int i = MathHelper.floor(f * h);
            int j = MathHelper.floor(f * h);
            int k = (context.getScaledWindowWidth() - i) / 2;
            int l = (context.getScaledWindowHeight() - j) / 2;
            int m = k + i;
            int n = l + j;
            RenderSystem.enableBlend();
            context.drawTexture(texture, k, l, -90, 0.0F, 0.0F, i, j, i, j);
            RenderSystem.disableBlend();
            context.fill(RenderLayer.getGuiOverlay(), 0, n, context.getScaledWindowWidth(), context.getScaledWindowHeight(), -90, Colors.BLACK);
            context.fill(RenderLayer.getGuiOverlay(), 0, 0, context.getScaledWindowWidth(), l, -90, Colors.BLACK);
            context.fill(RenderLayer.getGuiOverlay(), 0, l, k, n, -90, Colors.BLACK);
            context.fill(RenderLayer.getGuiOverlay(), m, l, context.getScaledWindowWidth(), n, -90, Colors.BLACK);
            ci.cancel();
        }
    }


}
