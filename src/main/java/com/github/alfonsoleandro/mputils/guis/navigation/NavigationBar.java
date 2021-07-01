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
package com.github.alfonsoleandro.mputils.guis.navigation;

import com.github.alfonsoleandro.mputils.itemstacks.MPItemStacks;
import com.github.alfonsoleandro.mputils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Series of items that must be present in every PaginatedGUI.
 * Serves the purpose of helping the users to navigate a GUI.
 */
public class NavigationBar {

    /**
     * The array of buttons inside this navigationBar.
     */
    protected final GUIButton[] buttons;

    /**
     * Creates a new Navigation bar with the default items.
     */
    public NavigationBar(){
        this.buttons = new GUIButton[9];
        setDefaultButtons();
    }

    /**
     * Creates a new navigation bar, with the given array of buttons.
     * @param buttons The buttons contained in this navigation bar.
     * @throws IllegalArgumentException Thrown if the size of the given array of buttons is not 9.
     */
    public NavigationBar(GUIButton[] buttons) throws IllegalArgumentException {
        if(buttons == null){
            this.buttons = new GUIButton[9];
            setDefaultButtons();
        }else {
            if(buttons.length != 9) throw new IllegalArgumentException("There must be 9 buttons in the buttons array");
            this.buttons = buttons;
        }
    }


    /**
     * Sets the default buttons for this Navigation Bar.
     * Previous page, empty slot*3, current page, empty slot*3 and next page.
     */
    public void setDefaultButtons(){
        GUIButton nextPage = new GUIButton("DEFAULT:next page",
                MPItemStacks.newItemStack(Material.ARROW,
                        1,
                        "&6&oNext page &6&l->",
                        Arrays.asList((StringUtils.colorizeString('&', "&6Click &fhere,&fto go to page %nextpage%")).split(","))),
                GUIButton.GUIButtonCondition.HAS_NEXT_PAGE,
                MPItemStacks.newItemStack(Material.PAPER,
                        1,
                        "&8&l*",
                        new ArrayList<>())
        );
        GUIButton previousPage = new GUIButton("DEFAULT:previous page",
                MPItemStacks.newItemStack(Material.ARROW,
                        1,
                        "&6&l<- &6&oPrevious page",
                        Arrays.asList((StringUtils.colorizeString('&', "&6Click &fhere,&fto go to page %previouspage%")).split(","))),
                GUIButton.GUIButtonCondition.HAS_PREVIOUS_PAGE,
                MPItemStacks.newItemStack(Material.PAPER,
                        1,
                        "&8&l*",
                        new ArrayList<>())
        );
        GUIButton currentPage = new GUIButton("DEFAULT:current page",
                MPItemStacks.newItemStack(Material.BOOK,
                        1,
                        "&f&lPage: &6%page%&f&l/&6%totalpages%",
                        new ArrayList<>()),
                GUIButton.GUIButtonCondition.ALWAYS,
                null
        );
        GUIButton emptySlot = new GUIButton("DEFAULT:empty slot",
                MPItemStacks.newItemStack(Material.PAPER,
                        1,
                        "&8&l*",
                        new ArrayList<>()),
                GUIButton.GUIButtonCondition.ALWAYS,
                null
        );


        buttons[0] = previousPage;
        buttons[1] = emptySlot;
        buttons[2] = emptySlot;
        buttons[3] = emptySlot;
        buttons[4] = currentPage;
        buttons[5] = emptySlot;
        buttons[6] = emptySlot;
        buttons[7] = emptySlot;
        buttons[8] = nextPage;
    }


    /**
     * Gets the button in the given position.
     * @param i The position of the button relative to the navigation bar (0-8).
     * @return The button in the given position.
     */
    public GUIButton getButtonAt(int i){
        return buttons[i];
    }

    /**
     * Sets the button in the given position.
     * @param i The position of the button relative to the navigation bar (0-8).
     * @param button The new button.
     */
    public void setButtonAt(int i, GUIButton button){
        this.buttons[i] = button;
    }


    /**
     * Gets the button array in this NavigationBar.
     * @return The array of buttons included in this NavigationBar.
     */
    public GUIButton[] getButtons(){
        return this.buttons;
    }

    /**
     * Adds the navigation bar to the PaginatedGUI.
     * @param inv The GUI inventory.
     * @param page The page the GUI is going to open in.
     * @param totalPages The total amount of pages of the PaginatedGUI.
     */
    public void addNavigationBar(Inventory inv, int page, int totalPages){
        int navBarSlot = inv.getSize()-9;
        for(int i = 0; i < 9; i++){
            inv.setItem(navBarSlot+i, this.buttons[i].getItem(page, totalPages));
        }
    }


}
