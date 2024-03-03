/*
Copyright (c) 2022 Leandro Alfonso

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
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.github.alfonsoleandro.mputils.guis;

import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import com.github.alfonsoleandro.mputils.itemstacks.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class for creating a GUI of a single page.
 *
 * @author alfonsoLeandro
 * @see PaginatedGUI
 * @since 0.9.0
 */
public class SimpleGUI extends GUI {

    /**
     * Creates an empty non-paginated CHEST type {@link InventoryType#CHEST} inventory for GUI purposes
     * with a title (not colored by default, can be passed colored) and a size.
     *
     * @param title   Inventory title.
     * @param size    Inventory size.
     * @param guiTags Any string tags you may want to add in order to differentiate a GUI from another.
     */
    public SimpleGUI(String title, int size, String guiTags) {
        super(title, size, guiTags, GUIType.SIMPLE);
    }

    /**
     * Sets an ItemStack in the given index, check inventory's indexes for reference.
     * Can replace an existing ItemStack if it was in the given index.
     *
     * @param index The index where to put the item.
     * @param item  The item to be put.
     */
    public void setItem(int index, ItemStack item) {
        if (index >= this.guiSize || index < 0) return;
        this.inventory.setItem(index, item);
    }

    /**
     * Adds an ItemStack to the GUI wherever it fits, if it fits.
     *
     * @param item The item to add.
     */
    @Override
    public void addItem(ItemStack item) {
        ItemStack toAdd = item.clone();
        this.inventory.addItem(toAdd);
    }

    /**
     * Changes the title of the inv. To do this, it creates a new inventory with the previous size
     * and the new title, then adds the items the previous inventory had.
     *
     * @param title The new title to use.
     */
    public void setTitle(String title) {
        ItemStack[] oldContents = this.inventory.getContents();
        this.inventory = Bukkit.createInventory(null, this.guiSize, title);
        this.inventory.setContents(oldContents);
    }

    /**
     * Removes all the items from the inventory.
     */
    @Override
    public void clearInventory() {
        this.inventory.clear();
    }

    /**
     * Check if a given item can be added totally to this GUI.
     * Even if this method returns false, the item may still be able to be partially added.
     *
     * @param item The item to check.
     * @return True if the item can be added, false otherwise.
     * @since 1.10.0
     */
    public boolean canAdd(ItemStack item) {
        return InventoryUtils.canAdd(item, this.inventory);
    }

    /**
     * Gets the contents of the inventory in this GUI.
     *
     * @return The contents of the inventory.
     * @since 1.10.0
     */
    public ItemStack[] getContents() {
        return this.inventory.getContents();
    }

    /**
     * Gets a hashmap containing every item in the inventory and its slot.
     *
     * @return The hashmap containing the items in this GUI.
     * @deprecated Items are now stored in an array, as items in the Inventory class do. Use {@link #getContents()} instead.
     */
    @Deprecated(since = "1.10.0", forRemoval = true)
    public HashMap<Integer, ItemStack> getItems() {
        ItemStack[] contents = this.inventory.getContents();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null) items.put(i, contents[i]);
        }
        return items;
    }


}
