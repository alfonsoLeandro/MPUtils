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

import com.github.alfonsoleandro.mputils.guis.navigation.GUIButton;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import com.github.alfonsoleandro.mputils.guis.navigation.NavigationBar;
import com.github.alfonsoleandro.mputils.guis.utils.PlayersOnGUIsManager;
import com.github.alfonsoleandro.mputils.itemstacks.MPItemStacks;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Class for creating a paginated GUI with unlimited pages.
 * Dynamically updates the number of pages according to the given List of ItemStacks.
 */
public class PaginatedGUI extends Navigable {

    /**
     * The total list of items throughout the entire GUI.
     */
    protected List<ItemStack> items;
    /**
     * The list of {@link ItemStack} per page.
     */
    protected HashMap<Integer, List<ItemStack>> pagesOfItems;

    /**
     * Creates a GUI of any size bigger than 9 and smaller than 54 slots, with ability to have various pages and a navigation bar in the last row.
     * Will create the gui, and set the default navBar items.
     *
     * @param title       The title for the GUI, not colorized by default.
     * @param sizePerPage The size of each page, must be multiple of 9, between (included) 9 and 54.
     * @param items       The list of all items to show in the GUI throughout all pages.
     * @param guiTags     Any string tags you may want to add in order to differentiate a GUI from another.
     * @param navBar      The navBar to use for this GUI.
     */
    public PaginatedGUI(String title, int sizePerPage, List<ItemStack> items, String guiTags, NavigationBar navBar) {
        super(title, sizePerPage, guiTags, GUIType.PAGINATED, navBar);

        this.items = items;
        updateItemsPerPage(items);
    }

    /**
     * Creates a GUI of any size bigger than 9 and smaller than 54 slots, with ability to have various pages and a navigation bar in the last row.
     * Will create the gui, and set the default navBar items. (Uses default navbar).
     *
     * @param title       The title for the GUI, not colorized by default.
     * @param sizePerPage The size of each page, must be multiple of 9, bigger than 9 and smaller than 54.
     * @param items       The list of all items to show in the GUI throughout all pages.
     * @param guiTags     Any string tags you may want to add in order to differentiate a GUI from another.
     */
    public PaginatedGUI(String title, int sizePerPage, List<ItemStack> items, String guiTags) {
        this(title, sizePerPage, items, guiTags, new NavigationBar());
    }

    /**
     * Adds an item to this GUI at the end of the list of items.
     *
     * @param item The item to add.
     */
    @Override
    public void addItem(ItemStack item) {
        this.items.add(item);
        updateItemsPerPage(this.items);
    }


    /**
     * Updates the page list for the GUI, setting the page size -9(-9 because of the navbar).
     * Used for changing the items contained in the GUI.
     *
     * @param items The total list of items to display throughout the entire paginated GUI.
     */
    public void updateItemsPerPage(List<ItemStack> items) {
        this.items = items;
        this.pagesOfItems = new HashMap<>();
        List<ItemStack> itemsOnAPage = new ArrayList<>();
        for (ItemStack item : items) {
            if (itemsOnAPage.size() >= this.guiSize - 9) {
                this.pagesOfItems.put(this.pagesOfItems.size(), itemsOnAPage);
                itemsOnAPage = new ArrayList<>();
            }
            itemsOnAPage.add(item);
        }
        if (!itemsOnAPage.isEmpty()) this.pagesOfItems.put(this.pagesOfItems.size(), itemsOnAPage);
        this.pages = this.pagesOfItems.size();
    }


    /**
     * Changes the size of each GUI page.
     *
     * @param size The size for each GUI page.
     */
    public void setSizePerPage(int size) {
        this.guiSize = size;
        updateItemsPerPage(this.items);
    }


    /**
     * Opens the GUI for a certain player in a specific page, sets the placeholders for the navBar items and lets the
     * {@link PlayersOnGUIsManager} know there is a player with open GUI.
     *
     * @param player The player to open the GUI for.
     * @param page   The page of the GUI to open for the player.
     */
    public void openGUI(Player player, int page) {
        if (player == null) return;
        if (page > this.pages) return;
        player.closeInventory();
        setPage(player, page);
        player.openInventory(this.inventory);
        PlayersOnGUIsManager.addPlayer(player.getName(), page, GUIType.PAGINATED, this);
    }

    /**
     * Changes the items inside the inventory for the items in the given page.
     *
     * @param player The player to set the GUI page for.
     * @param page   The page to set the items for.
     */
    @Override
    public void setPage(Player player, int page) {
        setItemsForPage(page);
        setNavBarForPage(page);
        PlayersOnGUIsManager.addPlayer(player.getName(), page, GUIType.PAGINATED, this);
    }

    /**
     * Sets the items from {@link PaginatedGUI#updateItemsPerPage(List)} for the desired page.
     *
     * @param page The page to look for in {@link PaginatedGUI#pagesOfItems}.
     */
    public void setItemsForPage(int page) {
        List<ItemStack> itemsOnPage = this.pagesOfItems.get(page);

        if (itemsOnPage == null || itemsOnPage.isEmpty()) {
            for (int i = 0; i < this.guiSize - 9; i++) {
                this.inventory.setItem(i, null);
            }
        } else {
            for (int i = 0; i < this.guiSize - 9; i++) {
                if (i < itemsOnPage.size()) {
                    this.inventory.setItem(i, itemsOnPage.get(i));
                } else {
                    this.inventory.setItem(i, null);
                }
            }
        }
    }

    /**
     * Clears the inventory and the item list for this PaginatedGUI.
     */
    @Override
    public void clearInventory() {
        this.inventory.clear();
        this.items.clear();
    }


    /**
     * Opens this PaginatedGUI in page 0 for the given player.
     *
     * @param player The player to open the GUI for.
     * @see #openGUI(Player, int)
     */
    @Override
    public void openGUI(Player player) {
        this.openGUI(player, 0);
    }


    /**
     * Gets a list of all the items contained throughout all the pages in this GUI.
     *
     * @return The list of items.
     */
    public List<ItemStack> getItems() {
        return this.items;
    }

    //<editor-fold desc="Deprecated methods" defaultstate="collapsed">

    /**
     * Sets the default items for the navBar buttons.
     *
     * @deprecated Now in NavigationBar class ({@link NavigationBar#setDefaultButtons()}).
     */
    @Deprecated
    public void setDefaultNavBarItems() {
        this.navBar.setDefaultButtons();
    }

    /**
     * Updates the navigation bar, called when the GUI is opened for a user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     * @deprecated Renamed to {@link #setNavBarForPage(int)}.
     */
    @Deprecated
    public void setNavBar(int page) {
        this.setNavBarForPage(page);
    }


    /**
     * Choose whether do you want an item in the medium slot of the navigation bar (last row),
     * usually used for telling the player the page they are on.
     *
     * @param hasCurrentPageItem true if you want the item to be shown, or false if you want the medium slot to be the same as navBar item {@link PaginatedGUI#setNavbarItem(ItemStack)}
     * @deprecated Please use {@link GUIButton.GUIButtonCondition} instead.
     */
    @Deprecated
    public void setHasCurrentPageItem(boolean hasCurrentPageItem) {
        this.navBar.getButtonAt(4).setCondition(hasCurrentPageItem ?
                GUIButton.GUIButtonCondition.ALWAYS :
                GUIButton.GUIButtonCondition.NEVER);
    }


    /**
     * Sets the item to be shown as the "next page" button, situated in the right corner of the navigation bar.
     * Can use the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param nextPageItem The item in question.
     * @deprecated Now you must manually add an item to the
     * {@link NavigationBar} and manually add {@link GUIButton} to it.
     */
    @Deprecated
    public void setNextPageItem(ItemStack nextPageItem) {
        this.navBar.getButtonAt(8).setItem(nextPageItem);
    }

    /**
     * Sets the item to be shown as the "previous page" button, situated in the left corner of the navigation bar.
     * Can use the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param previousPageItem The item in question.
     * @deprecated Now you must manually add an item to the
     * {@link NavigationBar} and manually add {@link GUIButton} to it.
     */
    @Deprecated
    public void setPreviousPageItem(ItemStack previousPageItem) {
        this.navBar.getButtonAt(0).setItem(previousPageItem);
    }

    /**
     * Sets the item to be shown as the "current page" button, situated in the center of the navigation bar.
     * Only shown if {@link PaginatedGUI#setHasCurrentPageItem(boolean)} is set to true, else, will show {@link PaginatedGUI#setNavbarItem(ItemStack)}
     * Can use the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param currentPageItem The item in question.
     * @deprecated Now you must manually add an item to the
     * {@link NavigationBar} and manually add {@link GUIButton} to it.
     */
    @Deprecated
    public void setCurrentPageItem(ItemStack currentPageItem) {
        this.navBar.getButtonAt(4).setItem(currentPageItem);
    }

    /**
     * Sets the item to be shown in the navbar for slots where there are no buttons.
     *
     * @param navbarItem The item in question.
     * @deprecated Now you must manually add an item to the
     * {@link NavigationBar} and manually add {@link GUIButton} to it.
     */
    @Deprecated
    public void setNavbarItem(ItemStack navbarItem) {
        this.navBar.getButtonAt(1).setItem(navbarItem);
        this.navBar.getButtonAt(0).setBackupItem(navbarItem);
        this.navBar.getButtonAt(8).setBackupItem(navbarItem);
    }

    /**
     * Gives the "Previous page" navigation bar button
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     * @deprecated Now instead of previous page, current page, next page and nav bar items, you can add
     * up to 9 different buttons to any PaginatedGUI using {@link NavigationBar} and {@link GUIButton}.
     */
    @Deprecated
    public ItemStack getPreviousPageItem(int page) {
        return MPItemStacks.replacePlaceholders(this.navBar.getButtonAt(0).getRawItem().clone(),
                getPlaceHoldersMap(page, this.pages));
    }


    /**
     * Gives the navigation bar ItemStack for empty navBar slots.
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     * @deprecated Now instead of previous page, current page, next page and nav bar items, you can add
     * up to 9 different buttons to any PaginatedGUI using {@link NavigationBar} and {@link GUIButton}.
     */
    @Deprecated
    public ItemStack getNavBarItem(int page) {
        return this.navBar.getButtonAt(1).getItem(page, this.pages);
    }

    /**
     * Gives the "Current page" navigation bar button
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     * @deprecated Now instead of previous page, current page, next page and nav bar items, you can add
     * up to 9 different buttons to any PaginatedGUI using {@link NavigationBar} and {@link GUIButton}.
     */
    @Deprecated
    public ItemStack getCurrentPageItem(int page) {
        return this.navBar.getButtonAt(4).getItem(page, this.pages);
    }

    /**
     * Gives the "Next page" navigation bar button
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     * @deprecated Now instead of previous page, current page, next page and nav bar items, you can add
     * up to 9 different buttons to any PaginatedGUI using {@link NavigationBar} and {@link GUIButton}.
     */
    @Deprecated
    public ItemStack getNextPageItem(int page) {
        return MPItemStacks.replacePlaceholders(this.navBar.getButtonAt(8).getRawItem().clone(),
                getPlaceHoldersMap(page, this.pages));
    }


    /**
     * Gets a Map with every placeholder available for this object with every corresponding value.
     *
     * @param page The page the GUI is in.
     * @return A map containing every placeholder and every value.
     * @deprecated Moved to {@link GUIButton}.
     */
    @Deprecated
    private Map<String, String> getPlaceHoldersMap(int page, int totalPages) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%page%", String.valueOf(page + 1));
        placeholders.put("%nextpage%", String.valueOf(page + 2));
        placeholders.put("%previouspage%", String.valueOf(page));
        placeholders.put("%totalpages%", String.valueOf(totalPages));

        return placeholders;
    }
    //</editor-fold>

}