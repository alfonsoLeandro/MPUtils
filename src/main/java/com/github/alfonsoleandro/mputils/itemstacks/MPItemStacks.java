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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Class containing {@link ItemStack} related methods.
 */
public final class MPItemStacks {

    /**
     * Private constructor so this class cannot be instantiated
     */
    private MPItemStacks(){}


    /**
     * Replaces every placeholder from a map of placeholders with their corresponding value from the same map.
     * It works on display names and lore lines.
     *
     * @param itemStack The item to modify.
     * @param placeholders The map containing every placeholder with its corresponding value in the follwoing way:
     *                     (placeholder : value).
     * @return The ItemStack with its name and lore placeholders replaced, null if the ItemStack was null,
     * or the same ItemStack if its type was AIR
     */
    public static ItemStack replacePlaceholders(ItemStack itemStack, Map<String,String> placeholders){
        if(itemStack == null || itemStack.getType().equals(Material.AIR)) return itemStack;

        ItemMeta meta = itemStack.getItemMeta();

        if(meta.hasDisplayName()){
            String displayName = meta.getDisplayName();
            for (String key: placeholders.keySet()) {
                displayName = displayName.replace(key, placeholders.get(key));
            }
            meta.setDisplayName(displayName);
        }
        if(meta.hasLore()){
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
        assert meta != null;

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
}
