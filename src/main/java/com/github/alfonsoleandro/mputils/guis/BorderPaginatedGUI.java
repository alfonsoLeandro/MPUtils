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

import com.github.alfonsoleandro.mputils.guis.navigation.BorderGUINavigationBar;
import com.github.alfonsoleandro.mputils.guis.navigation.NavigationBar;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for creating a paginated GUI with a simple border all around the items inside itself.
 * See example here: https://imgur.com/a/DZDMsqU
 */
public class BorderPaginatedGUI extends PaginatedGUI {

    /**
     * Creates a new BorderPaginatedGUI with a border made up of {@link com.github.alfonsoleandro.mputils.guis.navigation.GUIButton} around the contained items.
     * The total size for this GUI will always be of 54, with 26 free slots for items to be placed on each page.
     * @param title       The title for the GUI, not colorized by default.
     * @param items       The list of all items to show in the GUI throughout all pages.
     * @param guiTags     Any string tags you may want to add in order to differentiate a GUI from another.
     * @param navBar      The navBar to use for this GUI.
     */
    public BorderPaginatedGUI(String title, List<ItemStack> items, String guiTags, BorderGUINavigationBar navBar) {
        super(title, 54, items, guiTags, navBar);
    }

    /**
     * Creates a new BorderPaginatedGUI with a border made up of {@link com.github.alfonsoleandro.mputils.guis.navigation.GUIButton} around the contained items.
     * The total size for this GUI will always be of 54, with 26 free slots for items to be placed on each page.
     * Uses the default {@link BorderGUINavigationBar}.
     * @param title       The title for the GUI, not colorized by default.
     * @param items       The list of all items to show in the GUI throughout all pages.
     * @param guiTags     Any string tags you may want to add in order to differentiate a GUI from another.
     */
    public BorderPaginatedGUI(String title, List<ItemStack> items, String guiTags) {
        super(title, 54, items, guiTags, new BorderGUINavigationBar());
    }

    /**
     * Updates the page list for the GUI (28 items per page).
     * Used for changing the items contained in the GUI.
     *
     * @param items The total list of items to display throughout the entire paginated GUI.
     */
    @Override
    public void updateItemsPerPage(List<ItemStack> items) {
        this.items = items;
        this.pagesOfItems = new HashMap<>();
        List<ItemStack> itemsOnAPage = new ArrayList<>();
        for (ItemStack item : items) {
            if(itemsOnAPage.size() >= 28) {
                this.pagesOfItems.put(this.pagesOfItems.size(), itemsOnAPage);
                itemsOnAPage = new ArrayList<>();
            }
            itemsOnAPage.add(item);
        }
        if(!itemsOnAPage.isEmpty()) this.pagesOfItems.put(this.pagesOfItems.size(), itemsOnAPage);
        this.pages = this.pagesOfItems.size();
    }

    /**
     * The size of a BorderPaginatedGUI is always 54. You cannot change this size.
     * @param size N/A.
     */
    @Override
    public void setSizePerPage(int size) {
        throw new UnsupportedOperationException("The size of a BorderPaginatedGUI is always 54. You cannot change this size.");
    }

    /**
     * Gets the NavigationBar associated to this GUI.
     * @return The BorderedGUINavigationBar associated with this GUI.
     */
    @Override
    public BorderGUINavigationBar getNavBar(){
        return (BorderGUINavigationBar) this.navBar;
    }

    /**
     * Sets the navigation bar that this GUI will be using.
     * @param navBar The navigation bar you want this GUI to use.
     * @see NavigationBar
     * @see BorderGUINavigationBar
     * @throws IllegalArgumentException If the given NavigationBar is not an instance of BorderGUINavigationBar.
     */
    @Override
    public void setNavBar(NavigationBar navBar){
        if(!(navBar instanceof BorderGUINavigationBar))
            throw new IllegalArgumentException("The Navigation Bar for a BorderPaginatedGUI MUST be a BorderedGUINavigationBar.");
        this.navBar = navBar;
    }

    /**
     * Sets the items from {@link PaginatedGUI#updateItemsPerPage(List)} for the desired page.
     *
     * @param page The page to look for in {@link PaginatedGUI#pagesOfItems}.
     */
    @Override
    public void setItemsForPage(int page){
        List<ItemStack> itemsOnPage = this.pagesOfItems.get(page);
        int[] itemSlots = new int[]{10,11,12,13,14,15,16,
        19,20,21,22,23,24,25,
        28,29,30,31,32,33,34,
        37,38,39,40,41,42,43};

        if(itemsOnPage == null || itemsOnPage.isEmpty()){
            for(int i : itemSlots){
                this.inventory.setItem(i, null);
            }
        }else{
            for(int i = 0; i < itemSlots.length; i++) {
                if(i < itemsOnPage.size()) {
                    this.inventory.setItem(itemSlots[i], itemsOnPage.get(i));
                } else {
                    this.inventory.setItem(itemSlots[i], null);
                }
            }
        }
    }
}
