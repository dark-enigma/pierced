package net.dark.pierced.entity.custom;

import net.dark.pierced.Pierced;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class HarpoonModel<T extends HarpoonEntity> extends SinglePartEntityModel<T> {	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final EntityModelLayer HARPOON = new EntityModelLayer(Identifier.of(Pierced.MOD_ID,"harpoon"),"main");
	private final ModelPart root;

	public HarpoonModel(ModelPart root) {

		this.root = root.getChild("root");

    }


	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(14, 0).cuboid(-0.5F, -14.0F, -0.5F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 8).cuboid(-3.5F, -20.0F, 0.0F, 7.0F, 8.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 17).cuboid(-2.5F, -11.0F, 0.0F, 5.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild("Tip_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -20.0F, 0.0F, 7.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		root.render(matrices, vertexConsumer, light, overlay, color);

	}


	@Override
	public ModelPart getPart() {
		return root;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

	}
}

