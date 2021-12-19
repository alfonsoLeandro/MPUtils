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
package com.github.alfonsoleandro.mputils.guis;

import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class for creating a GUI of a single page.
 * @see PaginatedGUI
 */
public class SimpleGUI extends GUI{

    /**
     * Hashmap containing every item that has been added to the GUI.
     */
    private final HashMap<Integer, ItemStack> items;


    /**
     * Creates an empty non-paginated CHEST type {@link InventoryType#CHEST} inventory for GUI purposes
     * with a title (not colored by default, can be passed colored) and a size.
     *
     * @param title Inventory title.
     * @param size Inventory size.
     * @param guiTags Any string tags you may want to add in order to differentiate a GUI from another.
     */
    public SimpleGUI(String title, int size, String guiTags){
        super(title, size, guiTags, GUIType.SIMPLE);
        this.items = new HashMap<>();
    }

    /**
     * Sets an ItemStack in the given index, check inventory's indexes for reference.
     * Can replace an existing ItemStack if it was in the given index.
     *
     * @param index The index where to put the item.
     * @param item The item to be put.
     */
    public void setItem(int index, ItemStack item){
        if(index >= this.guiSize || index < 0) return;
        this.inventory.setItem(index, item);
        this.items.put(index, item);
    }

    /**
     * Adds an ItemStack to the GUI wherever it fits, if it fits.
     *
     * @param item The item to add.
     */
    @Override
    public void addItem(ItemStack item){
        this.inventory.addItem(item);
        for(int i = 0; i < this.guiSize; i++){
            ItemStack inI = this.inventory.getItem(i);
            if(inI != null && inI.isSimilar(item)){
                this.items.put(i, item);
            }
        }
    }

    /**
     * Changes the title of the inv. To do this, it creates a new inventory with the previous size
     * and the new title, then adds the items the previous inventory had.
     *
     * @param title The new title to use.
     */
    public void setTitle(String title){
        this.inventory = Bukkit.createInventory(null, this.guiSize, title);
        for(int i : this.items.keySet()){
            this.inventory.setItem(i, this.items.get(i));
        }

    }

    /**
     * Removes all the items from the inventory.
     */
    @Override
    public void clearInventory(){
        this.inventory.clear();
        this.items.clear();
    }

    /**
     * Gets a hashmap containing every item in the inventory and its slot.
     * @return The hashmap containing the items in this GUI.
     */
    public HashMap<Integer, ItemStack> getItems(){
        return this.items;
    }


}
