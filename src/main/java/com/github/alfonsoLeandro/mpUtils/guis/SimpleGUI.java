package com.github.alfonsoLeandro.mpUtils.guis;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class for creating a GUI of a single page. Check {@link PaginatedGUI} for a paginated GUI.
 */
public class SimpleGUI extends GUI{

    /**
     * Hashmap containing every item that has been added to the GUI.
     */
    final private HashMap<Integer, ItemStack> items;


    /**
     * Creates an empty non-paginated CHEST type {@link InventoryType#CHEST} inventory for GUI purposes
     * with a title (not colored by default, can be passed colored) and a size.
     *
     * @param title Inventory title.
     * @param size Inventory size.
     * @param guiTags Any string tags you may want to add in order to differentiate a GUI from another.
     */
    public SimpleGUI(String title, int size, String guiTags){
        super(title, size, guiTags);
        items = new HashMap<>();
    }

    /**
     * Sets an ItemStack in the given index, check inventory's indexes for reference.
     * Can replace an existing ItemStack if it was in the given index.
     *
     * @param index The index where to put the item.
     * @param item The item to be put.
     */
    public void setItem(int index, ItemStack item){
        if(index >= guiSize || index < 0) return;
        inventory.setItem(index, item);
        items.put(index, item);
    }

    /**
     * Adds an ItemStack to the GUI wherever it fits, if it fits.
     *
     * @param item The item to add.
     */
    public void addItem(ItemStack item){
        inventory.addItem(item);
        for(int i = 0; i < guiSize; i++){
            if(inventory.getItem(i)!= null && inventory.getItem(i).isSimilar(item)){
                items.put(i, item);
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
        inventory = Bukkit.createInventory(null, guiSize, title);
        for(int i : items.keySet()){
           inventory.setItem(i, items.get(i));
        }

    }

    /**
     * Removes all the items from the inventory.
     */
    public void clearInventory(){
        inventory.clear();
        items.clear();
    }

    /**
     * Gets a hashmap containing every item in the inventory and its slot.
     * @return The hashmap containing the items in this GUI.
     */
    public HashMap<Integer, ItemStack> getItems(){
        return this.items;
    }


}
