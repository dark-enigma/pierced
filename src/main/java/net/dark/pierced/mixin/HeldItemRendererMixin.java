package net.dark.pierced.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean vsas$renderCustomXBows(ItemStack stack, Item item, Operation<Boolean> original) {
        // Check if the item is a crossbow

        if (item == Items.CROSSBOW) {
            // Return true if it's either the default crossbow or a custom crossbow item
            return original.call(stack, item) || stack.getItem() instanceof CrossbowItem;
        } else {
            // For non-crossbow items, call the original method
            return original.call(stack, item);
        }
    }



}

