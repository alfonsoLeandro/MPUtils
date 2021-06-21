/*
Copyright (c) 2020 Leandro Alfonso

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.github.alfonsoleandro.mputils.itemstacks;

import com.github.alfonsoleandro.mputils.string.StringUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class containing {@link ItemStack} related methods.
 */
public final class MPItemStacks {

    /**
     * Private constructor so this class cannot be instantiated
     */
    private MPItemStacks(){
        throw new IllegalStateException("MPItemStacks is only a utility class!");
    }


    /**
     * Replaces every placeholder from a map of placeholders with their corresponding value from the same map.
     * It works on display names and lore lines.
     *
     * @param itemStack The item to modify.
     * @param placeholders The map containing every placeholder with its corresponding value in the following way:
     *                     (placeholder : value).
     * @return The ItemStack with its name and lore placeholders replaced, null if the ItemStack was null,
     * or the same ItemStack if its type was AIR
     */
    public static ItemStack replacePlaceholders(ItemStack itemStack, Map<String,String> placeholders){
        if(itemStack == null || itemStack.getType().equals(Material.AIR)) return itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return itemStack;

        if(meta.hasDisplayName()){
            String displayName = meta.getDisplayName();
            for (String key: placeholders.keySet()) {
                displayName = displayName.replace(key, placeholders.get(key));
            }
            meta.setDisplayName(displayName);
        }
        if(meta.getLore() != null){
            List<String> lore = new ArrayList<>();
            for (String line: meta.getLore()) {
                for (String key : placeholders.keySet()) {
                    line = line.replace(key, placeholders.get(key));
                }
                lore.add(line);
            }

            meta.setLore(lore);
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Helps on the making of a new {@link ItemStack} setting its lore an display name.
     * Colorizes the lore and display name using {@link StringUtils#colorizeString(char, String)} using {@literal '&'} as the alternateColorCode.
     *
     * @param material The material for the ItemStack.
     * @param amount The size of the stack.
     * @param displayName The display nam for the item.
     * @param lore The lore to be added. If none wanted, a {@code new ArrayList<>()} should be acceptable.
     * @return The new ItemStack with the given properties.
     */
    public static ItemStack newItemStack(Material material, int amount, String displayName, List<String> lore){
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return itemStack;

        if(displayName != null && !displayName.equalsIgnoreCase("")) {
            meta.setDisplayName(StringUtils.colorizeString('&', displayName));
        }

        List<String> resultingLore = new ArrayList<>();
        for(String line : lore){
            resultingLore.add(StringUtils.colorizeString('&', line));
        }
        meta.setLore(resultingLore);
        
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    /**
     * Serializes an inventory: saves inventory type, size, and contents.
     * @param inv The inventory to serialize.
     * @return A string that can be deserialized using {@link #deserializeInventory(String)}.
     * @see #serializeContents(ItemStack[])
     */
    public static String serializeInventory(Inventory inv){
        Map<String, Object> properties = new HashMap<>();
        properties.put("type", inv.getType());
        properties.put("size", inv.getSize());
        properties.put("contents", serializeContents(inv.getContents()));

        return new Gson().toJson(properties);
    }

    /**
     * Loads an inventory from a given string.
     * The given string must be provided by {@link #serializeInventory(Inventory)} or have the same format.
     * @param invString The String object that represents an inventory.
     * @return The inventory that the string argument represents.
     * @throws InvalidConfigurationException If the given String contains a contents String that
     * is invalid for {@link YamlConfiguration#loadFromString(String)} use.
     */
    public static Inventory deserializeInventory(String invString) throws InvalidConfigurationException {
        Map<String, Object> inventoryProperties = new Gson().fromJson(invString,
                new TypeToken<Map<String, Object>>(){}.getType());
        String type = (String) inventoryProperties.get("type");
        int size = (Integer) inventoryProperties.get("size");
        ItemStack[] contents = deserializeContents(
                (String)inventoryProperties.get("contents"),
                size);

        Inventory inv;
        if(type.equalsIgnoreCase("CHEST")){
            inv = Bukkit.createInventory(null, size);
        }else{
            inv = Bukkit.createInventory(null, InventoryType.valueOf(type));
        }
        inv.setContents(contents);

        return inv;
    }


    /**
     * Saves an array of ItemStacks to a String, preserves null spaces.
     * @param contents The contents of an inventory.
     * @return A string object that represents the given array of ItemStacks.
     */
    public static String serializeContents(ItemStack[] contents){
        YamlConfiguration tempConfig = new YamlConfiguration();
        for(int i = 0; i < contents.length; i++){
            ItemStack item = contents[i];
            tempConfig.set(String.valueOf(i), item);
        }

        return tempConfig.saveToString();
    }


    /**
     * Loads an array of ItemStacks from a given String.
     * The String must be provided by {@link #serializeContents(ItemStack[])} or have the same format.
     * @param contentsString The contents of the array.
     * @param invSize The size of the inventory that these contents will be added to.
     * @return An array of ItemStacks with the size equal to {@code invSize}.
     * @throws InvalidConfigurationException If the given String is invalid for {@link YamlConfiguration#loadFromString(String)} use.
     */
    public static ItemStack[] deserializeContents(String contentsString, int invSize) throws InvalidConfigurationException {
        YamlConfiguration tempConfig = new YamlConfiguration();
        tempConfig.loadFromString(contentsString);
        ItemStack[] contents = new ItemStack[invSize];

        for (int i = 0; i < invSize; i++) {
            contents[i] = tempConfig.getItemStack(String.valueOf(i));
        }
        return contents;

    }
}
