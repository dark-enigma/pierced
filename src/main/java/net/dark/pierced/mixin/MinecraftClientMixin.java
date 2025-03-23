package net.dark.pierced.mixin;

import net.dark.pierced.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MinecraftClientMixin {
    @Shadow public abstract Arm getMainArm();

    @Inject(method = "isUsingSpyglass", at = @At("HEAD"), cancellable = true)
    protected void isUsingSpyglass(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack activeItem = player.getActiveItem();
        if (player.isSneaking() && (player.getMainHandStack().isOf(ModItems.LONGCROSSBOW) || player.getOffHandStack().isOf(ModItems.LONGCROSSBOW))) {
            cir.setReturnValue(true);
        }
    }
}