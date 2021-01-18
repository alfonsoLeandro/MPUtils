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


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Custom event for when a GUI closes, called when a player who is being GUI managed by MPUtils closes a GUI.
 */
public class GUICloseEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final GUIType guiType;
    private final int page;
    private final InventoryCloseEvent event;
    private final String guiTags;
    private final GUI gui;


    /**
     * Custom event for registering when a player closes a GUI inventory.
     *
     * @param player The player that was using the GUI.
     * @param guiType The {@link GUIType} clicked, either {@link GUIType#PAGINATED} or {@link GUIType#SIMPLE}
     * @param page The page the clicker was on when closing the GUI, or -1 if the {@link GUIType} is {@link GUIType#SIMPLE}
     * @param event The bukkit {@link InventoryCloseEvent} fired, for you to modify it at your will.
     * @param guiTags Any string tags you may want to add in order to differentiate a GUI from another.
     * @param gui The gui object, can be simple or paginated, use {@link GUIClickEvent#getGuiType()} to check whether it is a paginated gui or a simple gui.
     */
    public GUICloseEvent(Player player, GUIType guiType, int page, InventoryCloseEvent event, String guiTags, GUI gui) {
        this.player = player;
        this.guiType = guiType;
        this.page = page;
        this.event = event;
        this.guiTags = guiTags;
        this.gui = gui;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Get the player who clicked the inventory.
     *
     * @return Player who clicked.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the GUI type of the clicked GUI.
     *
     * @return the type of GUI, either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     */
    public GUIType getGuiType() {
        return guiType;
    }

    /**
     * Gets the page the player was standing on when clicking the GUI.
     *
     * @return -1 if {@link GUIType} is equal to {@link GUIType#SIMPLE} or the page number in any other case.
     */
    public int getPage() {
        return page;
    }

    /**
     * Gets the actual {@link InventoryCloseEvent} that was fired when clicking the GUI.
     *
     * @return Said event.
     */
    public InventoryCloseEvent getInventoryCloseEvent() {
        return event;
    }

    /**
     * Gets the tags added to the GUI when creating an instance of the object.
     *
     * @return The tags previously entered.
     */
    public String getGuiTags() {
        return guiTags;
    }

    /**
     * Gets the instance of the GUI object so you can get the navBar items.
     *
     * @return Instance of the object {@link SimpleGUI} or {@link PaginatedGUI}.
     */
    public GUI getGui() {
        return gui;
    }
}
