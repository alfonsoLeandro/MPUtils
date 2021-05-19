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

import com.github.alfonsoleandro.mputils.itemstacks.MPItemStacks;
import com.github.alfonsoleandro.mputils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Class for creating a paginated GUI with unlimited pages. Dynamically updates the number of pages according to the given List of ItemStacks.
 */
public class PaginatedGUI extends GUI{

    /**
     * The total nu,ber of pages this GUI has.
     */
    private int pages;
    /**
     * The total list of items throughout the entire GUI.
     */
    private List<ItemStack> items;
    /**
     * The list of {@link ItemStack} per page.
     */
    private HashMap<Integer, List<ItemStack>> pagesOfItems;
    /**
     * Setting for whether showing or not a "Current page item" generally used for displaying info i.e: total number of pages.
     */
    private boolean hasCurrentPageItem = true;
    /**
     * The GUI button "Current page".
     */
    private ItemStack currentPageItem;
    /**
     * The GUI button "Previous page".
     */
    private ItemStack previousPageItem;
    /**
     * The GUI button "Next page".
     */
    private ItemStack nextPageItem;
    /**
     * The GUI ItemStack for empty navigation bar slots.
     */
    private ItemStack navbarItem;


    /**
     * Creates a GUI of any size bigger than 9 and smaller that 54 slots, with ability to have various pages and a navigation bar in the last row.
     * Will create the gui, and set the default navBar items
     *
     * @param title       The title for the GUI, not colorized by default.
     * @param sizePerPage The size of each page, must be multiple of 9, bigger than 9 and smaller than 54.
     * @param items       The list of all items to show in the GUI throughout all pages.
     * @param guiTags Any string tags you may want to add in order to differentiate a GUI from another.
     */
    public PaginatedGUI(String title, int sizePerPage, List<ItemStack> items, String guiTags) {
        super(title, sizePerPage, guiTags, GUIType.PAGINATED);

        this.items = items;
        updateItemsPerPage(items);
        setDefaultNavBarItems();
    }

    public void addItem(ItemStack item){
        this.items.add(item);
    }


    /**
     * Updates the page list for the GUI, setting the page size -9(-9 because of the navbar.
     * Used for changing the items contained in the GUI.
     *
     * @param items The total list of items to display throughout the entire paginated GUI.
     */
    public void updateItemsPerPage(List<ItemStack> items) {
        this.items = items;
        pagesOfItems = new HashMap<>();
        List<ItemStack> itemsOnAPage = new ArrayList<>();
        for (ItemStack item : items) {
            if(itemsOnAPage.size() >= guiSize - 9) {
                pagesOfItems.put(pagesOfItems.size(), itemsOnAPage);
                itemsOnAPage = new ArrayList<>();
            }
            itemsOnAPage.add(item);
        }
        if(!itemsOnAPage.isEmpty()) pagesOfItems.put(pagesOfItems.size(), itemsOnAPage);
        pages = pagesOfItems.size();
    }


    /**
     * Sets the default items for the navBar buttons.
     */
    public void setDefaultNavBarItems() {
        nextPageItem = MPItemStacks.newItemStack(Material.ARROW,
                1,
                "&6&oNext page &6&l->",
                Arrays.asList((StringUtils.colorizeString('&', "&6Click &fhere,&fto go to page %nextpage%")).split(",")));

        currentPageItem = MPItemStacks.newItemStack(Material.BOOK,
                1,
                "&f&lPage: &6%page%&f&l/&6%totalpages%",
                new ArrayList<>());

        previousPageItem = MPItemStacks.newItemStack(Material.ARROW,
                1,
                "&6&l<- &6&oPrevious page",
                Arrays.asList((StringUtils.colorizeString('&', "&6Click &fhere,&fto go to page %previouspage%")).split(",")));

        navbarItem = MPItemStacks.newItemStack(Material.PAPER,
                1,
                "&8&l*",
                new ArrayList<>());
    }

    /**
     * Updates the nav bar, called when the GUI is opened for an user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     */
    public void setNavBar(int page) {
        ItemStack previousPageItem = getPreviousPageItem(page);
        ItemStack navbarItem = getNavBarItem(page);
        ItemStack currentPageItem = getCurrentPageItem(page);
        ItemStack nextPageItem = getNextPageItem(page);

        if(page > 0) {
            inventory.setItem(guiSize - 9, previousPageItem);
        } else {
            inventory.setItem(guiSize - 9, navbarItem);
        }


        for (int i = guiSize - 8; i < guiSize - 1; i++) {
            if(i == guiSize - 5) continue;
            inventory.setItem(i, navbarItem);

        }

        if(hasCurrentPageItem) {
            inventory.setItem(guiSize - 5, currentPageItem);
        } else {
            inventory.setItem(guiSize - 5, navbarItem);
        }


        if(page+1 < pages) {
            inventory.setItem(guiSize - 1, nextPageItem);
        } else {
            inventory.setItem(guiSize - 1, navbarItem);
        }
    }

    /**
     * Changes the size of each GUI page.
     *
     * @param size The size for each GUI page.
     */
    public void setSizePerPage(int size) {
        this.guiSize = size;
        updateItemsPerPage(items);
    }


    /**
     * Choose whether or not do you want an item in the mid slot of the navigation bar (last row),
     * usually used for telling the player the page they are on.
     *
     * @param hasCurrentPageItem true if you want the item to be shown, or false if you want the mid slot to be the same as navBar item {@link PaginatedGUI#setNavbarItem(ItemStack)}
     */
    public void setHasCurrentPageItem(boolean hasCurrentPageItem) {
        this.hasCurrentPageItem = hasCurrentPageItem;
    }


    /**
     * Sets the item to be shown as the "next page" button, situated in the right corner of the navigation bar.
     * Can use the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param nextPageItem The item in question.
     */
    public void setNextPageItem(ItemStack nextPageItem) {
        this.nextPageItem = nextPageItem;
    }

    /**
     * Sets the item to be shown as the "previous page" button, situated in the left corner of the navigation bar.
     * Can use the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param previousPageItem The item in question.
     */
    public void setPreviousPageItem(ItemStack previousPageItem) {
        this.previousPageItem = previousPageItem;
    }

    /**
     * Sets the item to be shown as the "current page" button, situated in the center of the navigation bar.
     * Only shown if {@link PaginatedGUI#setHasCurrentPageItem(boolean)} is set to true, else, will show {@link PaginatedGUI#setNavbarItem(ItemStack)}
     * Can use the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param currentPageItem The item in question.
     */
    public void setCurrentPageItem(ItemStack currentPageItem) {
        this.currentPageItem = currentPageItem;
    }

    /**
     * Sets the item to be shown in the navbar for slots where there are no buttons.
     *
     * @param navbarItem The item in question.
     */
    public void setNavbarItem(ItemStack navbarItem) {
        this.navbarItem = navbarItem;
    }

    /**
     * Gives the "Previous page" navigation bar button
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     */
    public ItemStack getPreviousPageItem(int page){
        ItemStack item = new ItemStack(this.previousPageItem);

        return MPItemStacks.replacePlaceholders(item, getPlaceHoldersMap(page));
    }


    /**
     * Gives the navigation bar ItemStack for empty navBar slots.
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     */
    public ItemStack getNavBarItem(int page){
        ItemStack item = new ItemStack(this.navbarItem);

        return MPItemStacks.replacePlaceholders(item, getPlaceHoldersMap(page));
    }

    /**
     * Gives the "Current page" navigation bar button
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     */
    public ItemStack getCurrentPageItem(int page){
        ItemStack item = new ItemStack(this.currentPageItem);

        return MPItemStacks.replacePlaceholders(item, getPlaceHoldersMap(page));
    }

    /**
     * Gives the "Next page" navigation bar button
     *
     * @param page the page number to replace placeholders.
     * @return The ItemStack with replaced placeholders.
     */
    public ItemStack getNextPageItem(int page){
        ItemStack item = new ItemStack(this.nextPageItem);

        return MPItemStacks.replacePlaceholders(item, getPlaceHoldersMap(page));
    }

    /**
     * Gets a Map with every placeholder available for this object with every corresponding value.
     * @param page The page the GUI is in.
     * @return A map containing every placeholder and every value.
     */
    public Map<String, String> getPlaceHoldersMap(int page){
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%page%", String.valueOf(page+1));
        placeholders.put("%nextpage%", String.valueOf(page+2));
        placeholders.put("%previouspage%", String.valueOf(page));
        placeholders.put("%totalpages%", String.valueOf(pages));

        return placeholders;
    }

    /**
     * @return The total number of pages in the GUI.
     */
    public int getPages(){
        return this.pages;
    }

    /**
     * Opens the GUI for a certain player in a specific page, sets the placeholders for the navBar items and lets the
     * {@link PlayersOnGUIsManager} know there is a player with open GUI.
     *
     * @param player The player to open the GUI for.
     * @param page   The page of the GUI to open for the player.
     */
    public void openGUI(Player player, int page) {
        if(page > pages) return;
        player.closeInventory();
        setNavBar(page);
        setItemsForPage(page);
        player.openInventory(inventory);
        PlayersOnGUIsManager.addPlayer(player.getName(), page, GUIType.PAGINATED, this);
    }

    /**
     * Sets the items from {@link PaginatedGUI#updateItemsPerPage(List)} for the desired page.
     *
     * @param page The page to look for in {@link PaginatedGUI#pagesOfItems}.
     */
    public void setItemsForPage(int page){
        List<ItemStack> itemsOnPage = pagesOfItems.get(page);

        if(itemsOnPage == null || itemsOnPage.isEmpty()){
            for(int i = 0; i < guiSize -9; i++){
                inventory.setItem(i, new ItemStack(Material.AIR));

            }
            return;
        }

        for(int i = 0; i < guiSize -9; i++){
            if(i < itemsOnPage.size()) {
                inventory.setItem(i, itemsOnPage.get(i));
            }else{
                inventory.setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    /**
     * Clears the inventory and the item list for this PaginatedGUI.
     */
    public void clearInventory(){
        this.inventory.clear();
        this.items.clear();
    }


    /**
     * Opens this PaginatedGUI in page 0 for the given player.
     * @param player The player to open the GUI for.
     */
    @Override
    public void openGUI(Player player){
        this.openGUI(player, 0);
    }


    /**
     * Gets a list of all the items contained throughout all the pages in this GUI.
     * @return The list of items.
     */
    public List<ItemStack> getItems(){
        return this.items;
    }


}