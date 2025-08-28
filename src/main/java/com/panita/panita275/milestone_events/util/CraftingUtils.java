package com.panita.panita275.milestone_events.util;

import org.bukkit.Material;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingUtils {
    /**
     * Get the total amount of items crafted in a CraftItemEvent, accounting for shift-click crafting.
     * @param event The CraftItemEvent to analyze.
     * @return The total number of items crafted.
     */
    public static int getCraftedAmount(CraftItemEvent event) {
        int resultAmount = event.getRecipe().getResult().getAmount();

        // Normal Click
        if (!event.isShiftClick()) {
            return resultAmount;
        }

        // Shift Click for Shaped
        if (event.getRecipe() instanceof ShapedRecipe shaped) {
            return calculateShiftClickCraftAmount(event.getInventory(), shaped.getChoiceMap(), resultAmount);
        }

        // Shifit Click for Shapeless
        if (event.getRecipe() instanceof ShapelessRecipe shapeless) {
            return calculateShiftClickCraftAmount(event.getInventory(), shapeless.getChoiceList(), resultAmount);
        }

        // Fallback
        return resultAmount;
    }

    /**
     * Calculate the maximum number of items that can be crafted with a shift-click in a crafting inventory.
     * @param inv The crafting inventory.
     * @param choiceMap The map of recipe choices for shaped recipes.
     * @param resultAmount The amount of items produced per craft.
     * @return The total number of items that can be crafted.
     */
    private static int calculateShiftClickCraftAmount(CraftingInventory inv, Map<Character, RecipeChoice> choiceMap, int resultAmount) {
        Map<RecipeChoice, Integer> requirements = new HashMap<>();
        for (RecipeChoice choice : choiceMap.values()) {
            if (choice != null) {
                requirements.put(choice, requirements.getOrDefault(choice, 0) + 1);
            }
        }

        return calculateMaxCrafts(inv, requirements, resultAmount);
    }

    /**
     * Calculate the maximum number of items that can be crafted with a shift-click in a crafting inventory.
     * @param inv The crafting inventory.
     * @param choices The list of recipe choices for shapeless recipes.
     * @param resultAmount The amount of items produced per craft.
     * @return The total number of items that can be crafted.
     */
    private static int calculateShiftClickCraftAmount(CraftingInventory inv, List<RecipeChoice> choices, int resultAmount) {
        Map<RecipeChoice, Integer> requirements = new HashMap<>();
        for (RecipeChoice choise : choices) {
            requirements.put(choise, requirements.getOrDefault(choise, 0) + 1);
        }

        return calculateMaxCrafts(inv, requirements, resultAmount);
    }

    /**
     * Calculate the maximum number of items that can be crafted based on the inventory and recipe requirements.
     * @param inv The crafting inventory.
     * @param requirements A map of recipe choices to their required amounts.
     * @param resultAmount The amount of items produced per craft.
     * @return The total number of items that can be crafted.
     */
    private static int calculateMaxCrafts(CraftingInventory inv, Map<RecipeChoice, Integer> requirements, int resultAmount) {
        int maxCrafts = Integer.MAX_VALUE;

        for (Map.Entry<RecipeChoice, Integer> req : requirements.entrySet()) {
            RecipeChoice choice = req.getKey();
            int neededCraft = req.getValue();

            // Count how many of this choice are in the inventory
            int totalAvailable = 0;
            for (ItemStack ingredient : inv.getMatrix()) {
                if (ingredient != null && ingredient.getType() != Material.AIR && choice.test(ingredient)) {
                    totalAvailable += ingredient.getAmount();
                }
            }

            int craftsWithThis = totalAvailable / neededCraft;
            maxCrafts = Math.min(maxCrafts, craftsWithThis);
        }

        return maxCrafts == Integer.MAX_VALUE ? 0 : maxCrafts * resultAmount;
    }
}
