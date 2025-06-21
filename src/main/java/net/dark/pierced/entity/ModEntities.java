package net.dark.pierced.entity;

import net.dark.pierced.Pierced;
import net.dark.pierced.entity.custom.HarpoonEntity;
import net.dark.pierced.item.custom.Harpoon;
import net.dark.pierced.item.custom.HarpoonCrossbow;
import net.dark.pierced.item.custom.LongCrossbow;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<HarpoonEntity> HARPOON = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Pierced.MOD_ID, "harpoon"),
            EntityType.Builder.<HarpoonEntity>create(HarpoonEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f)
                    .build()
    );


    public static void registerModEntities() {
        System.out.println("Registering Mod Entities for pierced");
    }
}
