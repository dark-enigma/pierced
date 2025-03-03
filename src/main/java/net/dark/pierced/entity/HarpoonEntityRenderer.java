package net.dark.pierced.entity;

import net.dark.pierced.Pierced;
import net.dark.pierced.entity.custom.HarpoonEntity;
import net.dark.pierced.entity.custom.HarpoonModel;
import net.minecraft.client.render.*;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;


import static com.ibm.icu.impl.CurrencyData.provider;
import static java.awt.Color.*;

public class HarpoonEntityRenderer extends EntityRenderer<HarpoonEntity> {
    public static final Identifier TEXTURE = Identifier.of(Pierced.MOD_ID,"textures/entity/harpoon.png");
    public static final RenderLayer CHAIN_LAYER = RenderLayer.getEntityCutoutNoCull(Identifier.of(Pierced.MOD_ID, "textures/entity/chain.png"));
    private final HarpoonModel model;

    public HarpoonEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new HarpoonModel(context.getPart(HarpoonModel.HARPOON));
    }

    public void render(HarpoonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float yawAngle = MathHelper.lerpAngleDegrees(tickDelta, entity.prevYaw, entity.getYaw());
        float pitchAngle = MathHelper.lerpAngleDegrees(tickDelta, entity.prevPitch, entity.getPitch());


        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));

        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, entity.getItemStack().hasGlint());
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        if (entity.getOwner()!=null){
            Entity owner = entity.getOwner();
            if (owner instanceof LivingEntity livingOwner) {
                matrices.push();
                Vec3d entityPos = entity.getLerpedPos(tickDelta);

                Vec3d ringOffset = new Vec3d(0.0F, 0.0F, 0.0F)
                        .rotateX(pitchAngle * ((float) Math.PI / 180F))
                        .rotateY((yawAngle + 90.0F) * ((float) Math.PI / 180F))
                        .add(0.0F, (entity.getHeight()) / 2.0F, 0.0F);

                Vec3d leashPos = livingOwner.getLerpedPos(tickDelta);

                Vec3d eyePos = livingOwner.getCameraPosVec(tickDelta).subtract(0, 0.4, 0);

                // Option B (alternative): If getCameraPosVec() isn't available, do:
                // Vec3d eyePos = livingOwner.getLerpedPos(tickDelta)
                //       .add(0.0, livingOwner.getEyeHeight(livingOwner.getPose()), 0.0);

                // The vector from the hook to the player's eye
                Vec3d ownerPos = eyePos.subtract(entityPos);

                // Compute the chain length (used for texture V coords)
                float length = (float) ringOffset.distanceTo(ownerPos);


                MatrixStack.Entry matrixEntry = matrices.peek();
                Matrix4f modelMatrix = matrixEntry.getPositionMatrix();
                Matrix3f normalMatrix = matrixEntry.getNormalMatrix();

                float minU = 0.0F;
                float maxU = 1.0F;

                float minV = 0.0F;
                float maxV = length / 8.0F;

                VertexConsumer vertexConsumer2 = vertexConsumers.getBuffer(CHAIN_LAYER);


                Vec3d offset = ownerPos.subtract(ringOffset).normalize().multiply(0.25F, 0.0F, 0.25F).rotateY((float) Math.PI / 2F);
                Vec3d vert1 = ringOffset.add(offset);
                Vec3d vert2 = ownerPos.add(offset);
                Vec3d vert3 = ownerPos.subtract(offset);
                Vec3d vert4 = ringOffset.subtract(offset);

                int chainLight = WorldRenderer.getLightmapCoordinates(entity.getWorld(), livingOwner.getBlockPos());

                vertexConsumer2.vertex(modelMatrix, (float) vert1.x, (float) vert1.y, (float) vert1.z)
                        .color(255, 255, 255, 255)
                        .texture(minU, minV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(light)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);

                vertexConsumer2.vertex(modelMatrix, (float) vert2.x, (float) vert2.y, (float) vert2.z)
                        .color(255, 255, 255, 255)
                        .texture(minU, maxV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(chainLight)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);

                vertexConsumer2.vertex(modelMatrix, (float) vert3.x, (float) vert3.y, (float) vert3.z)
                        .color(255, 255, 255, 255)
                        .texture(maxU, maxV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(chainLight)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);
                vertexConsumer2.vertex(modelMatrix, (float) vert4.x, (float) ((float) vert4.y), (float) vert4.z)
                        .color(255, 255, 255, 255)
                        .texture(maxU, minV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(light)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);

                Vec3d offset2 = offset.rotateY((float) Math.PI / 2F);


                Vec3d vert1b = ringOffset.add(offset2);
                Vec3d vert2b = ownerPos.add(offset2);
                Vec3d vert3b = ownerPos.subtract(offset2);
                Vec3d vert4b = ringOffset.subtract(offset2);


                vertexConsumer2.vertex(modelMatrix, (float) vert1b.x, (float) vert1b.y, (float) vert1b.z)
                        .color(255, 255, 255, 255)
                        .texture(minU, minV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(light)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);

                vertexConsumer2.vertex(modelMatrix, (float) vert2b.x, (float) vert2b.y, (float) vert2b.z)
                        .color(255, 255, 255, 255)
                        .texture(minU, maxV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(chainLight)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);

                vertexConsumer2.vertex(modelMatrix, (float) vert3b.x, (float) vert3b.y, (float) vert3b.z)
                        .color(255, 255, 255, 255)
                        .texture(maxU, maxV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(chainLight)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);

                vertexConsumer2.vertex(modelMatrix, (float) vert4b.x, (float) vert4b.y, (float) vert4b.z)
                        .color(255, 255, 255, 255)
                        .texture(maxU, minV)
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(light)
                        .normal(matrixEntry, 0.0F, 1.0F, 0.0F);


                matrices.pop();
            }
        }
    }






    @Override
    public Identifier getTexture(HarpoonEntity entity) {
        return TEXTURE;
    }
}
