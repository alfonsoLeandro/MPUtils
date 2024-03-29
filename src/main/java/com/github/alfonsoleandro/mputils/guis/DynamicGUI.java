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
 * See an example here: <a href="https://imgur.com/a/oS9UsJc">https://imgur.com/a/oS9UsJc</a>
 *
 * @author alfonsoLeandro
 * @since 1.8.1
 */
public class DynamicGUI extends Navigable<NavigationBar> {

    /**
     * The title for the GUI. Use on {@link #checkSize()}.
     */
    protected final String title;
    /**
     * Whether this GUI has more than one page.
     */
    protected boolean isPaginated;

    /**
     * Creates a new DynamicGUI with the default navigation bar.
     *
     * @param title   The title for the GUI.
     * @param guiTags The unique String that will identify this GUI and help distinguish it from another GUIs.
     * @deprecated In favor of {@link #DynamicGUI(String, String, boolean)}.
     */
    @Deprecated
    public DynamicGUI(String title, String guiTags) {
        this(title, guiTags, false, new NavigationBar());
    }

    /**
     * Creates a new DynamicGUI with the given navigation bar.
     *
     * @param title   The title for the GUI.
     * @param guiTags The unique String that will identify this GUI and help distinguish it from another GUIs.
     * @param navBar  The navigation bar to use in this GUI when it has more than one page.
     * @deprecated In favor of {@link #DynamicGUI(String, String, boolean, NavigationBar)}.
     */
    @Deprecated
    public DynamicGUI(String title, String guiTags, NavigationBar navBar) {
        this(title, guiTags, false, navBar);
        this.items = new ArrayList<>();
    }

    /**
     * Creates a new DynamicGUI with the default navigation bar.
     *
     * @param title      The title for the GUI.
     * @param guiTags    The unique String that will identify this GUI and help distinguish it from another GUIs.
     * @param itemsMerge Watches for added items, when true, if they are the same as an existing item, it will merge them.
     *                   If false, every item will be count as a separate item.
     * @since 1.10.0
     */
    public DynamicGUI(String title, String guiTags, boolean itemsMerge) {
        this(title, guiTags, itemsMerge, new NavigationBar());
    }

    /**
     * Creates a new DynamicGUI with the given navigation bar.
     *
     * @param title      The title for the GUI.
     * @param guiTags    The unique String that will identify this GUI and help distinguish it from another GUIs.
     * @param navBar     The navigation bar to use in this GUI when it has more than one page.
     * @param itemsMerge Watches for added items, when true, if they are the same as an existing item, it will merge them.
     *                   If false, every item will be count as a separate item.
     * @since 1.10.0
     */
    public DynamicGUI(String title, String guiTags, boolean itemsMerge, NavigationBar navBar) {
        super(title, 9, guiTags, GUIType.SIMPLE, itemsMerge, navBar);
        this.title = title;
        this.items = new ArrayList<>();
    }

    /**
     * Gets the amount of pages on this DynamicGUI.
     *
     * @return -1 if this GUI is not paginated, the amount of pages in any other case.
     */
    @Override
    public int getPages() {
        if (this.isPaginated) return (int) Math.ceil(this.items.size() / 45.0);
        return -1;
    }

    /**
     * Adds an item to the item list.
     * If {@link #itemsMerge} is true, it will try to merge the item with an existing one if it is similar.
     *
     * @param item The item to add.
     */
    @Override
    public void addItem(ItemStack item) {
        super.addItem(item);
        checkSize();
    }

    /**
     * Checks and corrects the size of the GUI, checking whether this GUI will be Paginated or Simple.
     */
    public void checkSize() {
        int previousSize = this.guiSize;

        this.isPaginated = this.items.size() > 54;

        if (this.isPaginated) {
            this.guiSize = 54;
        } else {
            this.guiSize = Math.max((int) Math.ceil(this.items.size() / 9.0) * 9, 9);
        }
        if (this.guiSize != previousSize) {
            this.inventory = Bukkit.createInventory(null, this.guiSize, this.title);
        }
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
     * Opens the GUI for the given player. In case the GUI has more than one page,
     * this will open the GUI in the page number 0.
     *
     * @param player The player to open the GUI for.
     */
    @Override
    public void openGUI(Player player) {
        if (player == null) return;
        player.closeInventory();
        if (this.isPaginated) {
            preparePage(player, 0);
        } else {
            prepareItemsForPage(0);
            PlayersOnGUIsManager.addPlayer(player.getName(), -1, GUIType.SIMPLE, this);
        }
        player.openInventory(this.inventory);
    }

    /**
     * Opens the GUI in the given page if the gui is paginated, in other case opens the first page.
     *
     * @param player The player to open the GUI for.
     * @param page   The page to try to open the GUi in.
     */
    public void openGUI(Player player, int page) {
        if (!this.isPaginated) {
            openGUI(player);
            return;
        }
        preparePage(player, page);
    }

    /**
     * Sets the items, from the items in this GUI, for the desired page.
     *
     * @param page The page to look for in the map of items per page.
     */
    public void prepareItemsForPage(int page) {
        List<ItemStack> itemsOnPage = this.isPaginated ?
                this.items.subList(45 * page, Math.min(this.items.size(), (45 * page) + 45))
                :
                this.items;
        int lastSlot = this.isPaginated ? this.guiSize - 9 : this.guiSize;

        if (itemsOnPage.isEmpty()) {
            for (int i = 0; i < lastSlot; i++) {
                this.inventory.setItem(i, null);
            }
        } else {
            for (int i = 0; i < lastSlot; i++) {
                if (i < itemsOnPage.size()) {
                    this.inventory.setItem(i, itemsOnPage.get(i));
                } else {
                    this.inventory.setItem(i, null);
                }
            }
        }
    }

    @Override
    public void preparePage(Player player, int page) {
        prepareItemsForPage(page);
        prepareNavBarForPage(page);
        PlayersOnGUIsManager.addPlayer(player.getName(), page, GUIType.PAGINATED, this);
    }


    /**
     * Checks whether this GUI is paginated.
     * Same as doing {@code getPages() > -1}.
     *
     * @return True if this GUI has more than one page.
     */
    public boolean isPaginated() {
        return this.isPaginated;
    }

    //<editor-fold desc="Deprecated methods" defaultstate="collapsed">

    /**
     * Sets the items from {@link PaginatedGUI#updateItemsPerPage(List)} for the desired page.
     *
     * @param page The page to look for in {@link PaginatedGUI#pagesOfItems}.
     * @deprecated Renamed to prepareItemsForPage
     */
    @Deprecated
    public void setItemsForPage(int page) {
        prepareItemsForPage(page);
    }
    //</editor-fold>
}
