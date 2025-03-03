package net.dark.pierced.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EntitySpawnS2CPacket.class)

public class projectilePacketFix {

    /**
     * Redirects MathHelper.clamp to remove velocity restrictions.
     */
    @Redirect(
            method = "<init>(ILjava/util/UUID;DDDFFLnet/minecraft/entity/EntityType;ILnet/minecraft/util/math/Vec3d;D)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;clamp(DDD)D"
            )
    )
    private double removeClamp(double value, double min, double max) {
        return value; // Return the raw velocity value without clamping.
    }
}
