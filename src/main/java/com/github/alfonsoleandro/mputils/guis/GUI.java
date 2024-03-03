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
import com.github.alfonsoleandro.mputils.guis.utils.PlayersOnGUIsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class for defining a GUI's base behaviour.
 *
 * @author alfonsoLeandro
 * @since 1.4.1
 */
public abstract class GUI {

    /**
     * Some extra tags you may add to differentiate between GUIs.
     */
    protected final String guiTags;
    /**
     * The actual inventory, where the GUI is supposed to go in.
     */
    protected Inventory inventory;
    /**
     * The size for the GUI. In case of a {@link PaginatedGUI} this is the size for each page.
     */
    protected int guiSize;

    /**
     * Constructor for ANY GUI with its essential features.
     *
     * @param title   The title for the inventory (colors must be applied before).
     * @param size    The size for the inventory.
     * @param guiTags Any String chosen to distinguish this GUI from another GUI.
     * @param guiType The type of GUI. Either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     */
    protected GUI(String title, int size, String guiTags, GUIType guiType) {
        size = getValidSize(size, guiType);

        this.guiSize = size;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.guiTags = guiTags;
    }

    /**
     * Gets a valid size for a GUI, depending on its type.
     *
     * @param size The raw size input, to sanitize.
     * @param guiType The type of GUI.
     * @return A valid size for the GUI, equal to the input size, if it were to be valid in the first place,
     * or the closest, valid one in other case.
     */
    public static int getValidSize(int size, GUIType guiType) {
        if (size > 54) size = 54;
        if (size % 9 != 0) size = (int) Math.floor(size / 9.0) * 9;
        if (guiType.equals(GUIType.PAGINATED) && size < 18) size = 18;
        if (size < 9) size = 9;
        return size;
    }

    /**
     * Opens the GUI for the given player. In case the GUI is an instance of {@link PaginatedGUI}
     * this will open the GUI in the page number 0.
     *
     * @param player The player to open the GUI for.
     */
    public void openGUI(Player player) {
        if (player == null) return;
        player.closeInventory();
        player.openInventory(this.inventory);
        PlayersOnGUIsManager.addPlayer(player.getName(), -1, GUIType.SIMPLE, this);
    }

    /**
     * Clears the inventory for this GUI object. Leaving it with no items.
     */
    public abstract void clearInventory();

    /**
     * Adds an item to the item list.
     * IMPORTANT: *ANY* implementation of this method should add a CLONE of the item passed by parameter, instead
     * of the item itself. This is to prevent some bugs where the item is set to air when it shouldn't, as well
     * as to prevent modifying the source item when trying to modify the item to add.
     *
     * @param item The item to add.
     */
    public abstract void addItem(ItemStack item);

    /**
     * Get the unique tags for this GUI.
     *
     * @return The GuiTags String for this GUI.
     */
    public String getGuiTags() {
        return this.guiTags;
    }


    /**
     * Get the size for this GUI, in case of {@link PaginatedGUI}, the given size is the size per page.
     *
     * @return The size of this GUI.
     */
    public int getSize() {
        return this.guiSize;
    }
}
