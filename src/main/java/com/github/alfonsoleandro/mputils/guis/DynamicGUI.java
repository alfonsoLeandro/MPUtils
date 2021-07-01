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

import com.github.alfonsoleandro.mputils.guis.navigation.Navigable;
import com.github.alfonsoleandro.mputils.guis.navigation.NavigationBar;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import com.github.alfonsoleandro.mputils.guis.utils.PlayersOnGUIsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * An unordered GUI that is a {@link SimpleGUI} if the amount of items is less than 54, or
 * a {@link PaginatedGUI} in any other case.
 */
public class DynamicGUI extends GUI implements Navigable {

    /**
     * The title for the GUI. Use on {@link #checkSize()}.
     */
    protected String title;
    /**
     * The list of items contained in this GUI.
     */
    protected List<ItemStack> items;
    /**
     * Whether or not this GUI has more than one page.
     */
    protected boolean isPaginated;
    /**
     * The navigation bar used when this GUI has more than one page.
     */
    protected NavigationBar navBar;

    /**
     * Creates a new DynamicGUI with the default navigation bar.
     * @param title The title for the GUI.
     * @param guiTags The unique String that will identify this GUI and help distinguish it from another GUIs.
     */
    public DynamicGUI(String title, String guiTags) {
        this(title, guiTags, new NavigationBar());
    }

    /**
     * Creates a new DynamicGUI with the given navigation bar.
     * @param title The title for the GUI.
     * @param guiTags The unique String that will identify this GUI and help distinguish it from another GUIs.
     * @param navBar The navigation bar to use in this GUI when it has more than one page.
     */
    public DynamicGUI(String title, String guiTags, NavigationBar navBar){
        super(title, 9, guiTags, GUIType.SIMPLE);
        this.title = title;
        this.items = new ArrayList<>();
        this.navBar = navBar;

    }

    /**
     * Clears the inventory for this GUI object. Leaving it with no items.
     */
    @Override
    public void clearInventory() {
        this.items.clear();
        checkSize();
    }

    /**
     * Adds an item to the item list.
     * @param item The item to add.
     */
    @Override
    public void addItem(ItemStack item) {
        this.items.add(item);
        checkSize();
    }

    /**
     * Opens the GUI for the given player. In case the GUI has more than one page,
     * this will open the GUI in the page number 0.
     * @param player The player to open the GUI for.
     */
    @Override
    public void openGUI(Player player){
        if(player == null) return;
        player.closeInventory();
        if(isPaginated){
            setPage(player, 0);
        }else {
            setItemsForPage(0);
            PlayersOnGUIsManager.addPlayer(player.getName(), -1, GUIType.SIMPLE, this);
        }
        player.openInventory(inventory);
    }

    /**
     * Opens the GUI in the given page if the gui is paginated, in other case opens the first page.
     * @param player The player to open the GUI for.
     * @param page The page to try to open the GUi in.
     */
    public void openGUI(Player player, int page){
        if(!isPaginated){
            openGUI(player);
            return;
        }
        setPage(player, page);
    }


    /**
     * Changes the items inside the inventory for the items in the given page.
     * @param player The player to set the GUI page for.
     * @param page The page to set the items for.
     */
    @Override
    public void setPage(Player player, int page){
        setItemsForPage(page);
        setNavBarForPage(page);
        PlayersOnGUIsManager.addPlayer(player.getName(), page, GUIType.PAGINATED, this);
    }


    /**
     * Sets the items from {@link PaginatedGUI#updateItemsPerPage(List)} for the desired page.
     *
     * @param page The page to look for in {@link PaginatedGUI#pagesOfItems}.
     */
    public void setItemsForPage(int page){
        List<ItemStack> itemsOnPage = items.subList(54*page, Math.min(items.size(), (54*page)+54));
        int lastSlot = isPaginated ? guiSize-9 : guiSize;

        if(itemsOnPage.isEmpty()){
            for(int i = 0; i < lastSlot; i++){
                inventory.setItem(i, null);
            }
        }else{
            for(int i = 0; i < lastSlot; i++) {
                if(i < itemsOnPage.size()) {
                    inventory.setItem(i, itemsOnPage.get(i));
                } else {
                    inventory.setItem(i, null);
                }
            }
        }
    }


    /**
     * Checks and corrects the size of the GUI, checking whether this GUI will be Paginated or Simple.
     */
    public void checkSize(){
        int previousSize = this.guiSize;
        if(items.size() <= 54){
            this.guiSize = (int) Math.ceil(items.size()/9.0)*9;
            this.isPaginated = false;
        }else{
            this.guiSize = 54;
            this.isPaginated = true;
        }
        if(this.guiSize != previousSize){
            this.inventory = Bukkit.createInventory(null, this.guiSize, this.title);
        }
    }


    /**
     * Updates the navigation bar, called when the GUI is opened for an user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     */
    @Override
    public void setNavBarForPage(int page) {
        this.navBar.addNavigationBar(inventory, page, getPages());
    }


    /**
     * Gets the navigation bar object that this GUI is currently using.
     * @return The NavigationBar object being used.
     */
    @Override
    public NavigationBar getNavBar(){
        return this.navBar;
    }

    /**
     * Sets the navigation bar that this GUI will be using.
     * @param navBar The navigation bar you want this GUI to use.
     * @see NavigationBar
     */
    @Override
    public void setNavBar(NavigationBar navBar){
        this.navBar = navBar;
    }

    /**
     * Gets the amount of pages on this DynamicGUI.
     * @return -1 if this GUI is not paginated, the amount of pages in any other case.
     */
    @Override
    public int getPages(){
        if(isPaginated) return (items.size()/54)+1;
        return -1;
    }
}
