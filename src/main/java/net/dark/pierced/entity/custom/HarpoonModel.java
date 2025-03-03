package net.dark.pierced.entity.custom;

import net.dark.pierced.Pierced;
import net.dark.pierced.entity.custom.HarpoonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class HarpoonModel<T extends HarpoonEntity> extends SinglePartEntityModel<T> {

	public static final EntityModelLayer HARPOON = new EntityModelLayer(Identifier.of(Pierced.MOD_ID,"harpoon"),"main");

	private final ModelPart bb_main;
	public HarpoonModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(8, 0).cuboid(0.0F, -1.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 9).cuboid(0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(0.0F, -14.0F, 0.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 12).cuboid(0.0F, -4.0F, 5.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 15).cuboid(0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 3).cuboid(0.0F, -1.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(8, 15).cuboid(0.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 14).cuboid(0.0F, -4.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 6).cuboid(0.0F, -6.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 15).cuboid(0.0F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 11).cuboid(0.0F, -2.0F, 4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 13).cuboid(0.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 15).cuboid(0.0F, -5.0F, -5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 9).cuboid(0.0F, -6.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 17).cuboid(0.0F, -5.0F, 4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 11).cuboid(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 17).cuboid(0.0F, -2.0F, -5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 0).cuboid(0.0F, -4.0F, -6.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 3).cuboid(0.0F, -4.0F, -6.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 17).cuboid(0.0F, -2.0F, -5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 12).cuboid(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 0).cuboid(0.0F, -14.0F, -1.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 6).cuboid(0.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}


    @Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		bb_main.render(matrices, vertexConsumer, light, overlay, color);

    }


	@Override
	public ModelPart getPart() {
		return bb_main;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

	}
}

