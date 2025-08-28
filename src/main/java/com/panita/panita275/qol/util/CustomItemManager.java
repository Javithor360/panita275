package com.panita.panita275.qol.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.panita.panita275.Panitacraft;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class CustomItemManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static JsonObject items = new JsonObject();
    private static File file;

    /** Result of attempting to save an item */
    public enum SaveItemResult {
        SUCCESS,
        DUPLICATE_NAME,
        ERROR
    }

    /** Initializes the CustomItemManager with the plugin's data folder */
    public static void init(File dataFolder) {
        file = new File(dataFolder, "customitems.json");
        loadItems();
    }

    /** Loads items from the JSON file into the items JsonObject */
    private static void loadItems() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                items = new JsonObject();
                saveItems();
            } else {
                items = gson.fromJson(new FileReader(file), JsonObject.class);
                if (items == null) items = new JsonObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            items = new JsonObject();
        }
    }

    /** Saves the current items JsonObject to the file */
    private static void saveItems() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(items, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves an ItemStack with a custom name.
     * The item is serialized to JSON and stored in a file.
     * A custom identifier is added to the item's metadata to ensure uniqueness.
     *
     * @param name The unique name to save the item under.
     * @param item The ItemStack to save.
     * @return true if the item was saved successfully, false if an item with the same name already exists or an error occurred.
     */
    public static SaveItemResult saveItem(String name, ItemStack item) {
        if (items.has(name)) return SaveItemResult.DUPLICATE_NAME;

        try {
            // Add a custom identifier to the item meta
            ItemStack targetItem = item.clone();
            ItemMeta meta = targetItem.getItemMeta();
            NamespacedKey key = new NamespacedKey(Panitacraft.getInstance(), "custom_item_id");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, name);

            // Serialize the complete item including meta
            Map<String, Object> serialized = targetItem.serialize();
            String json = gson.toJson(serialized);
            items.addProperty(name, json);

            // Save to file
            saveItems();
            return SaveItemResult.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return SaveItemResult.ERROR;
        }
    }

    /**
     * Retrieves a saved ItemStack by its custom name.
     * @param name The name of the saved item.
     * @return The ItemStack if found, or null if not found or an error occurred.
     */
    public static ItemStack getItem(String name) {
        if (!items.has(name)) return null;
        try {
            String json = items.get(name).getAsString();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(json, type);
            return ItemStack.deserialize(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Returns a set of all saved item names */
    public static Set<String> getAllItemNames() {
        return items.keySet();
    }

    /**
     * Checks if an ItemStack is a custom saved item by looking for the custom identifier in its metadata.
     * @param item The ItemStack to check.
     * @return true if the item is a custom saved item, false otherwise.
     */
    public static boolean isCustomItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(Panitacraft.getInstance(), "custom_item_id");
        return meta.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }
}
