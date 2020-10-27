package com.github.alfonsoLeandro.mpUtils.guis;

import org.bukkit.inventory.ItemStack;

public interface GUI {

    /**
     * Clears the inventory for this GUI object.
     */
    void clearInventory();

    /**
     * Adds an item to the item list.
     * @param item The item to add.
     */
    void addItem(ItemStack item);

    /**
     * Get the unique tags for this GUI.
     * @return The GuiTags String for this GUI.
     */
    String getGuiTags();
}
