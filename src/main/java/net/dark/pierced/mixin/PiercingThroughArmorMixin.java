package net.dark.pierced.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.projectile.ArrowEntity;


import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class PiercingThroughArmorMixin {

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), ordinal = 0)
    private float onDamage(float damageAmount, DamageSource damageSource) {

        if (damageSource.getSource() instanceof ArrowEntity arrow) {
            if (arrow.isShotFromCrossbow() && arrow.getPierceLevel() > 0) {
                LivingEntity entity = (LivingEntity) (Object) this;


                float armorValue = entity.getArmor();

                double piercingPercentage = 0.6;

                damageAmount = (float) (damageAmount*((armorValue*arrow.getPierceLevel()*piercingPercentage)+1))/(armorValue+1);


            }
        }
        return damageAmount;  // Return the modified damage
    }


}

