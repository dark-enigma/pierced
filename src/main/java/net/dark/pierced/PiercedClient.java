package net.dark.pierced;

import net.dark.pierced.entity.HarpoonEntityRenderer;
import net.dark.pierced.entity.ModEntities;
import net.dark.pierced.entity.custom.HarpoonEntity;
import net.dark.pierced.entity.custom.HarpoonModel;
import net.dark.pierced.util.HudOverlay;
import net.dark.pierced.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class PiercedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudOverlay.init();
        ModModelPredicates.registerModelPredicates();

        EntityModelLayerRegistry.registerModelLayer(HarpoonModel.HARPOON, HarpoonModel::getTexturedModelData);
        EntityRendererRegistry.INSTANCE.register(ModEntities.HARPOON, HarpoonEntityRenderer::new);


    }
}

