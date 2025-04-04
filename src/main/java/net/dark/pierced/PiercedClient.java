package net.dark.pierced;

import net.dark.pierced.entity.HarpoonEntityRenderer;
import net.dark.pierced.entity.ModEntities;
import net.dark.pierced.entity.custom.HarpoonModel;
import net.dark.pierced.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class PiercedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();

        EntityModelLayerRegistry.registerModelLayer(HarpoonModel.HARPOON, HarpoonModel::getTexturedModelData);
        EntityRendererRegistry.INSTANCE.register(ModEntities.HARPOON, HarpoonEntityRenderer::new);
        


    }
}

