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

import com.github.alfonsoleandro.mputils.guis.GUI;
import com.github.alfonsoleandro.mputils.guis.PaginatedGUI;
import com.github.alfonsoleandro.mputils.guis.SimpleGUI;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Custom event for when a GUI closes, called when a player who is being GUI managed by MPUtils closes a GUI.
 */
public class GUICloseEvent extends InventoryCloseEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    protected final GUIType guiType;
    protected final int page;
    protected final GUI gui;

    /**
     * Custom event for registering when a player closes a MPUtils GUI inventory.
     *
     * @param transaction {@link InventoryCloseEvent}'s InventoryView.
     * @param guiType The {@link GUIType} clicked, either {@link GUIType#PAGINATED} or {@link GUIType#SIMPLE}
     * @param page The page the clicker was on when closing the GUI, or -1 if the {@link GUIType} is {@link GUIType#SIMPLE}
     * @param gui The gui object, can be simple or paginated, use {@link GUIClickEvent#getGuiType()} to check whether it is a paginated gui or a simple gui.
     */
    public GUICloseEvent(InventoryView transaction, GUIType guiType, int page, GUI gui){
        super(transaction);
        this.guiType = guiType;
        this.page = page;
        this.gui = gui;
    }


    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets the GUI type of the clicked GUI.
     *
     * @return the type of GUI, either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     */
    public GUIType getGuiType() {
        return this.guiType;
    }

    /**
     * Gets the page the player was standing on when clicking the GUI.
     *
     * @return -1 if {@link GUIType} is equal to {@link GUIType#SIMPLE} or the page number in any other case.
     */
    public int getPage() {
        return this.page;
    }

    /**
     * Gets the actual {@link InventoryCloseEvent} that was fired when clicking the GUI.
     *
     * @return Said event.
     * @deprecated The way this event works has changed, you do not need to get the original event
     * since now this class extends {@link InventoryCloseEvent}.
     */
    @Deprecated
    public InventoryCloseEvent getInventoryCloseEvent() {
        return this;
    }

    /**
     * Gets the tags added to the GUI when creating an instance of the object.
     * Deprecated. Please use {@link GUI#getGuiTags()} instead.
     *
     * @return The tags previously entered.
     * @deprecated Use {@link GUI#getGuiTags()} instead.
     */
    @Deprecated
    public String getGuiTags() {
        return this.gui.getGuiTags();
    }

    /**
     * Gets the instance of the GUI object, so you can get the navBar items.
     *
     * @return Instance of the object {@link SimpleGUI} or {@link PaginatedGUI}.
     */
    public GUI getGui() {
        return this.gui;
    }
}
