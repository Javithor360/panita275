package com.panita.panita275.qol.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

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

    public static void init(File dataFolder) {
        file = new File(dataFolder, "customitems.json");
        loadItems();
    }

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

    private static void saveItems() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(items, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean saveItem(String name, ItemStack item) {
        try {
            Map<String, Object> serialized = item.serialize();
            String json = gson.toJson(serialized);
            items.addProperty(name, json);
            saveItems();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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

    public static Set<String> getAllItemNames() {
        return items.keySet();
    }
}
