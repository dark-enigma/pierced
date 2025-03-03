package net.dark.pierced.mixin;

import net.dark.pierced.item.ModItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class armanimations{
    @Unique
    public final ModelPart head;
    @Unique
    public final ModelPart hat;
    @Unique
    public final ModelPart body;
    @Unique
    public final ModelPart rightArm;
    @Unique
    public final ModelPart leftArm;
    @Unique
    public final ModelPart rightLeg;
    @Unique
    public final ModelPart leftLeg;

    protected armanimations(ModelPart head, ModelPart hat, ModelPart body, ModelPart rightArm, ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg) {
        this.head = head;
        this.hat = hat;
        this.body = body;
        this.rightArm = rightArm;
        this.leftArm = leftArm;
        this.rightLeg = rightLeg;
        this.leftLeg = leftLeg;
    }

    @Inject(method = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
            at = @At("TAIL"))
    private void test(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getMainHandStack();
        ItemStack itemStack2 = livingEntity.getOffHandStack();
        if ((itemStack.getItem() == ModItems.LONGCROSSBOW ||itemStack.getItem() == ModItems.HARPOONCROSSBOW )  && CrossbowItem.isCharged(itemStack)){
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);

        }
        if ((itemStack2.getItem() == ModItems.LONGCROSSBOW ||itemStack2.getItem() == ModItems.HARPOONCROSSBOW )   && CrossbowItem.isCharged(itemStack2)){
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);

        }
    }
}

