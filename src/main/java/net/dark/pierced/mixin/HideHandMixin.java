package net.dark.pierced.mixin;

import net.dark.pierced.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.dark.pierced.item.custom.LongCrossbow.USING;

@Mixin(net.minecraft.client.render.item.HeldItemRenderer.class)
public class HideHandMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void hideHand(
            AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci
    ) {
        // Check the main hand instead of the active item
        var mainHandItem = player.getMainHandStack().getItem();


        if (mainHandItem == ModItems.LONGCROSSBOW && USING) {

            ci.cancel();
        }
    }
}
