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

import com.github.alfonsoleandro.mputils.guis.navigation.NavigationBar;
import com.github.alfonsoleandro.mputils.guis.navigation.Navigator;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Represents a GUI that can be navigable (has Navigation bar and pages).
 *
 * @author alfonsoLeandro
 * @since 1.8.1
 */
public abstract class Navigable<N extends Navigator> extends GUI {

    /**
     * The navigation bar present in this GUI.
     */
    protected N navBar;
    /**
     * The total number of pages this GUI has.
     */
    protected int pages;
    /**
     * The total list of items throughout the entire GUI.
     */
    protected List<ItemStack> items;
    /**
     * Whether to check if add items are similar to those already in the GUI and merge them if so.
     */
    protected boolean itemsMerge;

    /**
     * Constructor for ANY Navigable GUI with its essential features.
     *
     * @param title   The title for the inventory (colors must be applied before).
     * @param size    The size for the inventory.
     * @param guiTags Any String chosen to distinguish this GUI from another GUI.
     * @param guiType The type of GUI. Either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     * @param navBar  The {@link NavigationBar} object that goes with this Navigable GUI.
     */
    protected Navigable(String title, int size, String guiTags, GUIType guiType, N navBar) {
        this(title, size, guiTags, guiType, false, navBar);
    }

    /**
     * Constructor for ANY Navigable GUI with its essential features.
     *
     * @param title   The title for the inventory (colors must be applied before).
     * @param size    The size for the inventory.
     * @param guiTags Any String chosen to distinguish this GUI from another GUI.
     * @param guiType The type of GUI. Either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     * @param itemsMerge Whether to check if items to add are similar to those already in the GUI and merge them if so.
     * @param navBar  The {@link NavigationBar} object that goes with this Navigable GUI.
     */
    protected Navigable(String title, int size, String guiTags, GUIType guiType, boolean itemsMerge, N navBar) {
        super(title, size, guiTags, guiType);
        this.itemsMerge = itemsMerge;
        this.navBar = navBar;
    }

    /**
     * Changes the items inside the inventory for the items in the given page.
     * Recommended to use when turning pages, over {@link #openGUI(Player, int)}.
     *
     * @param player The player to set the GUI page for.
     * @param page   The page to set the items for.
     * @since 1.10.0
     */
    public abstract void preparePage(Player player, int page);

    /**
     * Opens the GUI in the first page for a given player.
     *
     * @param player The player to open the GUI for.
     * @since 1.10.0
     */
    public void openGUI(Player player) {
        openGUI(player, 0);
    }

    /**
     * Opens the GUI in the given page for a given player.
     *
     * @param player The player to open the GUI for.
     * @param page   The page to open the GUI in.
     * @since 1.10.0
     */
    public abstract void openGUI(Player player, int page);

    /**
     * Adds an item to the item list.
     * If {@link #itemsMerge} is true, it will try to merge the item with an existing one if it is similar.
     *
     * @param item The item to add.
     */
    @Override
    public void addItem(ItemStack item) {
        ItemStack toAdd = item.clone();
        boolean added = false;
        if (this.itemsMerge) {
            for (ItemStack itemStack : this.items) {
                if (itemStack.isSimilar(toAdd)) {
                    int toAddAmount = toAdd.getAmount();
                    int originalAmount = itemStack.getAmount();

                    // Item has already the max amount
                    if (originalAmount >= itemStack.getMaxStackSize()) {
                        continue;
                    }

                    // Item would go above max amount, add as much as possible, then continue loop.
                    if (originalAmount + toAddAmount > itemStack.getMaxStackSize()) {
                        itemStack.setAmount(itemStack.getMaxStackSize());
                        toAddAmount -= itemStack.getMaxStackSize() - originalAmount;
                        toAdd.setAmount(toAddAmount);
                        continue;
                    }

                    // Item fully merged
                    itemStack.setAmount(itemStack.getAmount() + toAdd.getAmount());
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            this.items.add(toAdd);
        }
    }

    /**
     * Updates the navigation bar, called when the GUI is opened for a user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     * @since 1.10.0
     */
    public void prepareNavBarForPage(int page) {
        this.navBar.addNavigationBar(this.inventory, page, getPages());
    }

    /**
     * Gets the amount of pages on this NavigableGUI.
     *
     * @return -1 if this GUI is not paginated, the amount of pages in any other case.
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * Gets the navigation bar object that this GUI is currently using.
     *
     * @return The NavigationBar object being used.
     */
    public N getNavBar() {
        return this.navBar;
    }

    /**
     * Sets the navigation bar that this GUI will be using.
     *
     * @param navBar The navigation bar you want this GUI to use.
     * @see NavigationBar
     */
    public void setNavBar(N navBar) {
        this.navBar = navBar;
    }

    /**
     * Checks whether the items are merged when added or not.
     *
     * @return True if the items are merged when added.
     * @since 1.10.0
     */
    public boolean isItemsMerge() {
        return this.itemsMerge;
    }

    /**
     * Sets whether the items are merged when added or not.
     *
     * @param itemsMerge True if the items are merged when added.
     * @since 1.10.0
     */
    public void setItemsMerge(boolean itemsMerge) {
        this.itemsMerge = itemsMerge;
    }


    //<editor-fold desc="Deprecated methods" defaultstate="collapsed">

    /**
     * Updates the navigation bar, called when the GUI is opened for a user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     *
     * @deprecated Renamed to prepareNavBarForPage
     */
    @Deprecated
    public void setNavBarForPage(int page) {
        prepareNavBarForPage(page);
    }

    /**
     * Changes the items inside the inventory for the items in the given page.
     *
     * @param player The player to set the GUI page for.
     * @param page   The page to set the items for.
     *
     * @deprecated Renamed to preparePage
     */
    @Deprecated
    public void setPage(Player player, int page) {
        preparePage(player, page);
    }

    //</editor-fold>
}
