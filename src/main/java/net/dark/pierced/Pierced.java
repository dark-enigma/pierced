package net.dark.pierced;

import net.dark.pierced.entity.ModEntities;
import net.dark.pierced.entity.custom.HarpoonEntity;
import net.dark.pierced.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pierced implements ModInitializer {
	public static final String MOD_ID = "pierced";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		ModEntities.registerModEntities();
	}
}