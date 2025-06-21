package net.dark.pierced;

import net.dark.pierced.entity.HarpoonEntityRenderer;

import net.dark.pierced.entity.ModEntities;
import net.dark.pierced.entity.custom.HarpoonModel;
import net.dark.pierced.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PiercedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();

        EntityModelLayerRegistry.registerModelLayer(HarpoonModel.HARPOON, HarpoonModel::getTexturedModelData);

//        EntityRendererRegistry.INSTANCE.register(ModEntities.HARPOON, HarpoonEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.HARPOON, HarpoonEntityRenderer::new);






    }
}

