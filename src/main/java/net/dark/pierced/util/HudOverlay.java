package net.dark.pierced.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import static net.dark.pierced.item.custom.LongCrossbow.USING;

public class HudOverlay implements HudRenderCallback {

    private static boolean registered = false;
    private float spyglassScale;
    Identifier texture = Identifier.of("pierced", "textures/gui/scope_overlay.png");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (USING && minecraftClient.options.getPerspective().isFirstPerson()) {
//            drawContext.fill(0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight(), 0xFF000000);
//            RenderSystem.enableBlend();
//            RenderSystem.defaultBlendFunc();
//
//            Identifier texture = Identifier.of("pierced", "textures/gui/scope_overlay.png");
//            drawContext.drawTexture(texture, 0, 0, 0, 0, drawContext.getScaledWindowHeight(), drawContext.getScaledWindowHeight(), drawContext.getScaledWindowHeight(), drawContext.getScaledWindowHeight());
//            RenderSystem.disableBlend();

            float f = renderTickCounter.getLastFrameDuration();
            this.spyglassScale = MathHelper.lerp(0.5F * f, this.spyglassScale, 1.125F);


            this.renderSpyglassOverlay(drawContext, this.spyglassScale);
        }
    }

    public static void init() {
        if (!registered) {
            HudRenderCallback.EVENT.register(new HudOverlay());
            registered = true;
        }

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            // Handle any tick-based logic here without re-registering the HUD handler
        });
    }
    private void renderSpyglassOverlay(DrawContext context, float scale) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        float f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / f) * scale;
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
    }
}

