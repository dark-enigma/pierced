package net.dark.pierced.mixin;

import net.dark.pierced.item.ModItems;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentProjectileEntity.class)
public abstract class projectileChecker {

    @Shadow private @Nullable ItemStack weapon;

    @Inject(method = "isShotFromCrossbow", at = @At("HEAD"), cancellable = true)
    private void injectedIsShotFromCrossbow(CallbackInfoReturnable<Boolean> cir) {
        PersistentProjectileEntity self = (PersistentProjectileEntity) (Object) this;


        if (this.weapon != null && (this.weapon.isOf(Items.CROSSBOW) || weapon.isOf(ModItems.LONGCROSSBOW  ) ||weapon.isOf(ModItems.HARPOONCROSSBOW))) {
            cir.setReturnValue(true);
        }
    }
}


//public boolean isShotFromCrossbow() {
//		return this.weapon != null && this.weapon.isOf(Items.CROSSBOW);
//	}
