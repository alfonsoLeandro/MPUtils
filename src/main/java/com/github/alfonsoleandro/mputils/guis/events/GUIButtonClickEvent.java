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
package com.github.alfonsoleandro.mputils.guis.events;

import com.github.alfonsoleandro.mputils.guis.Navigable;
import com.github.alfonsoleandro.mputils.guis.PaginatedGUI;
import com.github.alfonsoleandro.mputils.guis.navigation.GUIButton;
import com.github.alfonsoleandro.mputils.guis.navigation.NavigationBar;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Custom event for GUI navbar button's clicks, called when a player, using a paginated GUI clicks a GUI navbar item.
 *
 * @author alfonsoLeandro
 * @since 1.8.1
 */
public class GUIButtonClickEvent extends GUIClickEvent {

    /**
     * The handlers list for this event.
     */
    private static final HandlerList HANDLERS = new HandlerList();
    /**
     * The button involved in this event.
     */
    protected final GUIButton clickedButton;
    /**
     * The slot the button was in, relative to the navigation bar.
     */
    protected final int navBarSlot;
    /**
     * Whether the button met its condition when clicked.
     */
    protected final boolean metCondition;


    /**
     * Custom event for GUIButton clicks, called when a player who is being GUI managed by MPUtils clicks a button
     * inside a GUI's navigation bar.
     * Should be used for turning pages, interactive GUIs and cancelling the {@link InventoryClickEvent}.
     *
     * @param view          {@link InventoryClickEvent}'s InventoryView.
     * @param type          {@link InventoryClickEvent}'s SlotType.
     * @param slot          {@link InventoryClickEvent}'s clicked slot.
     * @param click         {@link InventoryClickEvent}'s ClickType.
     * @param action        {@link InventoryClickEvent}'s InventoryAction.
     * @param key           {@link InventoryClickEvent}'s hotbar key pressed (if any).
     * @param page          The page the clicker was on when clicking, or -1 if the {@link GUIType} is {@link GUIType#SIMPLE}
     * @param gui           The PaginatedGUI object.
     * @param clickedButton The button that was involved in this event.
     * @param navBarSlot    The slot in which the clicked button was in relative to the navigation bar.
     * @see NavigationBar
     */
    public GUIButtonClickEvent(InventoryView view,
                               InventoryType.SlotType type,
                               int slot,
                               ClickType click,
                               InventoryAction action,
                               int key,
                               int page,
                               Navigable<?> gui,
                               GUIButton clickedButton,
                               int navBarSlot) {
        super(view, type, slot, click, action, key, GUIType.PAGINATED, page, gui, true);
        this.clickedButton = clickedButton;
        this.navBarSlot = navBarSlot;
        this.metCondition = clickedButton.getCondition().meetsCondition(page, gui.getPages());
    }

    /**
     * Spigot API required method.
     * @return A list of all the handlers for this event.
     */
    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Spigot API required method.
     * @return A list of all the handlers for this event.
     */
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the page the player was standing on when clicking the GUI.
     *
     * @return The page number the player was standing on when clicking the GUI.
     */
    @Override
    public int getPage() {
        return this.page;
    }

    /**
     * Gets the instance of the GUI object involved in this event.
     * For the pre included GUI classes, this GUI object will most of the time be an instance of
     * {@link PaginatedGUI} but not all the time, as it can also be an instance of {@link com.github.alfonsoleandro.mputils.guis.DynamicGUI}
     *
     * @return The Navigable GUI object.
     */
    @Override
    public Navigable<?> getGui() {
        return (Navigable<?>) this.gui;
    }

    /**
     * Gets the button involved in this event.
     *
     * @return The button responsible for triggering this event.
     */
    public GUIButton getClickedButton() {
        return this.clickedButton;
    }

    /**
     * Gets the slot of the button that was clicked.
     * Can be used to get the button using {@link NavigationBar#getButtonAt(int)}.
     *
     * @return The navigation bar slot the button was placed.
     */
    public int getNavBarSlot() {
        return this.navBarSlot;
    }

    /**
     * Gets whether the button met its condition needed for showing its main item or not.
     *
     * @return True if the button met its condition and was showing its main item the moment it was clicked.
     */
    public boolean buttonMetCondition() {
        return this.metCondition;
    }
}
