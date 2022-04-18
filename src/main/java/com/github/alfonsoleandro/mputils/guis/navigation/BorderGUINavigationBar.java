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
package com.github.alfonsoleandro.mputils.guis.navigation;

import com.github.alfonsoleandro.mputils.guis.BorderPaginatedGUI;
import com.github.alfonsoleandro.mputils.itemstacks.MPItemStacks;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Special {@link NavigationBar} used in {@link BorderPaginatedGUI}.
 */
public class BorderGUINavigationBar extends NavigationBar {

    /**
     * The array of buttons that go in the border of the {@link BorderPaginatedGUI}.
     */
    protected final GUIButton[] borderButtons;

    /**
     * Creates a new Navigation Bar with the default items.
     */
    public BorderGUINavigationBar(){
        super();
        this.borderButtons = new GUIButton[17];
        setDefaultBorderButtons();
    }

    /**
     * Creates a new navigation bar, with the given array of buttons.
     * @param buttons The buttons contained in this navigation bar.
     * @param borderButtons The buttons that go in the border of the BorderGUI.
     * @throws IllegalArgumentException Thrown if the size of the arrays is incorrect
     * (9 for the buttons, 17 for the border buttons).
     */
    public BorderGUINavigationBar(GUIButton[] buttons, GUIButton[] borderButtons) throws IllegalArgumentException {
        super(buttons);
        if(borderButtons == null){
            this.borderButtons = new GUIButton[17];
            setDefaultBorderButtons();
        }else {
            if(borderButtons.length != 9) throw new IllegalArgumentException("There must be 17 buttons in the border buttons array");
            this.borderButtons = buttons;
        }
    }

    /**
     * Gets the button in the given position.
     * @param i The position of the button relative to the navigation bar (0-25).
     * @return The button in the given position.
     */
    @Override
    public GUIButton getButtonAt(int i){
        if(i < 9) return this.buttons[i];
        return this.borderButtons[i-9];
    }

    /**
     * Sets the button in the given position.
     * @param i The position of the button relative to the navigation bar (0-25).
     * @param button The new button.
     */
    @Override
    public void setButtonAt(int i, GUIButton button){
        if(i < 9) {
            this.buttons[i] = button;
        }else{
            this.borderButtons[i-9] = button;
        }
    }


    /**
     * Sets the default buttons for this BorderGUI Navigation Bar.
     * previous page, empty slot*3, current page, empty slot*3 and next page from super.
     * empty slot*17 for the GUI border.
     */
    @Override
    public void setDefaultButtons(){
        super.setDefaultButtons();
        this.setDefaultBorderButtons();
    }

    /**
     * Sets the default border buttons for this BorderedGUINavigationBar.
     */
    public void setDefaultBorderButtons(){
        if(this.borderButtons == null) return;
        GUIButton guiBorder = new GUIButton("DEFAULT:guiBorder",
                MPItemStacks.newItemStack(Material.PAPER,
                        1,
                        "&8&l*",
                        new ArrayList<>()),
                GUIButton.GUIButtonCondition.ALWAYS,
                null);

        for(int i = 0; i < 17; i++){
            this.borderButtons[i] = guiBorder;
        }
    }

    /**
     * Adds the navigation bar to the PaginatedGUI.
     * @param inv The GUI inventory.
     * @param page The page the GUI is going to open in.
     * @param totalPages The total amount of pages of the PaginatedGUI.
     */
    @Override
    public void addNavigationBar(Inventory inv, int page, int totalPages){
        int btnSlot = 0;
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, this.buttons[btnSlot].getItem(page, totalPages));
            btnSlot++;
        }
        btnSlot = 0;
        for(int i = 0; i < 45; i++){
            if(i< 9 || i%9 == 0 || (i+1)%9 == 0){
                inv.setItem(i, this.borderButtons[btnSlot].getItem(page, totalPages));
                btnSlot++;
            }
        }
    }

    /**
     * Gets the array of buttons that make up the border of the GUI this Navigation bar belongs to.
     * @return The array of buttons from the border of this NavigationBar.
     */
    public GUIButton[] getBorderButtons(){
        return this.borderButtons;
    }

}
