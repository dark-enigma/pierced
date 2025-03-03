package net.dark.pierced.item;

import net.dark.pierced.Pierced;
import net.dark.pierced.item.custom.Harpoon;
import net.dark.pierced.item.custom.HarpoonCrossbow;
import net.dark.pierced.item.custom.LongCrossbow;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {


    public static final Item LONGCROSSBOW = registerItem("long_crossbow",new LongCrossbow(new Item.Settings().maxCount(1)));
    public static final Item HARPOONCROSSBOW = registerItem("harpoon_crossbow",new HarpoonCrossbow(new Item.Settings().maxCount(1)));
    public static final Item HARPOON = registerItem("harpoon",new Harpoon(new Item.Settings().maxCount(1)));



    private static Item registerItem(String name,Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Pierced.MOD_ID,name),item);
    }


    public static void registerModItems(){
        Pierced.LOGGER.info("Registering mod items for " + Pierced.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {

        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(LONGCROSSBOW);
            entries.add(HARPOONCROSSBOW);
            entries.add(HARPOON);
        });
    }
}
//private static final float DEFAULT_PULL_TIME = 1.25F;
//    public static final int RANGE = 8;
//    private static final float DEFAULT_SPEED = 3.15F;
//    private static final float FIREWORK_ROCKET_SPEED = 1.6F;