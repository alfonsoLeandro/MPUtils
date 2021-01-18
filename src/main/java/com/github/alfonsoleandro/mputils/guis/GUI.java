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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class inherited by {@link PaginatedGUI} and {@link SimpleGUI}.
 */
public abstract class GUI {

    /**
     * The actual inventory, where the GUI is supposed to go in.
     */
    protected Inventory inventory;
    /**
     * Some extra tags you may add to differentiate between GUIs.
     */
    final protected String guiTags;
    /**
     * The size for the GUI. In case of a {@link PaginatedGUI} this is the size for each page.
     */
    protected int guiSize;


    protected GUI(String title, int size, String guiTags) {
        if(size > 54) size = 54;
        if(size % 9 != 0) size = (int) Math.floor(size / 9.0);
        if(size <= 9) size = 18;

        this.guiSize = size;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.guiTags = guiTags;
    }

    /**
     * Opens the GUI for the given player. In case the GUI is an instance of {@link PaginatedGUI}
     * this will open the GUI in the page number 0.
     * @param player The player to open the GUI for.
     */
    public void openGUI(Player player){
        if(player == null) return;
        player.openInventory(inventory);
        PlayersOnGUIsManager.addPlayer(player.getName(), -1, GUIType.SIMPLE, guiTags, this);
    }

    /**
     * Clears the inventory for this GUI object.
     */
    public abstract void clearInventory();

    /**
     * Adds an item to the item list.
     * @param item The item to add.
     */
    public abstract void addItem(ItemStack item);

    /**
     * Get the unique tags for this GUI.
     * @return The GuiTags String for this GUI.
     */
    public String getGuiTags(){
        return this.guiTags;
    }


    /**
     * Get the size for this GUI, in case of {@link PaginatedGUI}, the given size is the size per page.
     * @return The size of this GUI.
     */
    public int getSize(){
        return this.guiSize;
    }
}
