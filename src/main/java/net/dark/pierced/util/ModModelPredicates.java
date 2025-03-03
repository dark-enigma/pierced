package net.dark.pierced.util;

import net.dark.pierced.item.ModItems;
import net.dark.pierced.item.custom.HarpoonCrossbow;
import net.dark.pierced.item.custom.LongCrossbow;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import static net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry.register;


public class ModModelPredicates {
    public static void registerModelPredicates() {
        registerCustomCrossbow();
        registerharpoonone();
    }





    private static void registerCustomCrossbow() {



        register(
                ModItems.LONGCROSSBOW,
                Identifier.ofVanilla("pull"),
                (stack, world, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        return LongCrossbow.isCharged(stack)
                                ? 0.0F
                                : (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / (float) LongCrossbow.getPullTime(stack, entity);
                    }
                }
        );
        register(
                ModItems.LONGCROSSBOW,
                Identifier.ofVanilla("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !LongCrossbow.isCharged(stack) ? 1.0F : 0.0F
        );
        register(ModItems.LONGCROSSBOW, Identifier.ofVanilla("charged"), (stack, world, entity, seed) -> LongCrossbow.isCharged(stack) ? 1.0F : 0.0F);
        register(ModItems.LONGCROSSBOW, Identifier.ofVanilla("firework"), (stack, world, entity, seed) -> {
            ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
            return chargedProjectilesComponent != null && chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
    private static void registerharpoonone() {



        register(
                ModItems.HARPOONCROSSBOW,
                Identifier.ofVanilla("pull"),
                (stack, world, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        return HarpoonCrossbow.isCharged(stack)
                                ? 0.0F
                                : (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / (float) HarpoonCrossbow.getPullTime(stack, entity);
                    }
                }
        );
        register(
                ModItems.HARPOONCROSSBOW,
                Identifier.ofVanilla("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !HarpoonCrossbow.isCharged(stack) ? 1.0F : 0.0F
        );
        register(ModItems.HARPOONCROSSBOW, Identifier.ofVanilla("charged"), (stack, world, entity, seed) -> HarpoonCrossbow.isCharged(stack) ? 1.0F : 0.0F);
        register(ModItems.HARPOONCROSSBOW, Identifier.ofVanilla("firework"), (stack, world, entity, seed) -> {
            ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
            return chargedProjectilesComponent != null && chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}



