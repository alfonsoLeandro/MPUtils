/*Copyright (c) 2022 Leandro Alfonso

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

import com.github.alfonsoleandro.mputils.guis.DynamicGUI;
import com.github.alfonsoleandro.mputils.guis.GUI;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Custom event for GUI clicks, called when a player who is being GUI managed by MPUtils clicks a GUI.
 * Should be used for turning pages, interactive GUIs and cancelling {@link InventoryClickEvent}
 */
public class GUIClickEvent extends InventoryClickEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    protected final GUIType guiType;
    protected final int page;
    protected final GUI gui;
    protected final boolean isButtonClick;


    /**
     * Custom event for GUI clicks, called when a player who is being GUI managed by MPUtils clicks a GUI.
     * Should be used for turning pages, interactive GUIs and cancelling the {@link InventoryClickEvent}.
     *
     * @param view          {@link InventoryClickEvent}'s InventoryView.
     * @param type          {@link InventoryClickEvent}'s SlotType.
     * @param slot          {@link InventoryClickEvent}'s clicked slot.
     * @param click         {@link InventoryClickEvent}'s ClickType.
     * @param action        {@link InventoryClickEvent}'s InventoryAction.
     * @param key           {@link InventoryClickEvent}'s hotbar key pressed (if any).
     * @param guiType       The {@link GUIType} clicked, either {@link GUIType#PAGINATED} or {@link GUIType#SIMPLE}
     * @param page          The page the clicker was on when clicking, or -1 if the {@link GUIType} is {@link GUIType#SIMPLE}
     * @param gui           The gui object, can be simple or paginated, use {@link GUIClickEvent#getGuiType()} to check whether it is a paginated gui or a simple gui.
     * @param isButtonClick Whether a Button was clicked and therefore called {@link GUIButtonClickEvent}.
     */
    public GUIClickEvent(InventoryView view,
                         InventoryType.SlotType type,
                         int slot,
                         ClickType click,
                         InventoryAction action,
                         int key,
                         GUIType guiType,
                         int page,
                         GUI gui,
                         boolean isButtonClick) {
        super(view, type, slot, click, action, key);
        this.guiType = guiType;
        this.page = page;
        this.gui = gui;
        this.isButtonClick = isButtonClick;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Get the player who clicked the inventory.
     *
     * @return Player who clicked.
     * @deprecated The way this event works has changed, you should now get the {@link org.bukkit.entity.HumanEntity}
     * that clicked the inventory since now this class extends {@link InventoryClickEvent}.
     */
    @Deprecated
    public Player getClicker() {
        return (Player) this.getWhoClicked();
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
     * Gets the actual {@link InventoryClickEvent} that was fired when clicking the GUI.
     *
     * @return Said event.
     * @deprecated The way this event works has changed, you do not need to get the original event
     * since now this class extends {@link InventoryClickEvent}.
     */
    @Deprecated
    public InventoryClickEvent getEvent() {
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
     * @return Instance of the GUI object.
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Gets the true index of the clicked slot, if it was a usable slot.
     * i.e: in a paginated gui, the first slot of the second page would be slot number 45.
     *
     * @return -1 if the click is outside the menu, or inside the navigation bar, the usable slot index in other case.
     */
    public int getTrueSlotClicked() {
        // Click outside the GUI.
        if (getRawSlot() >= this.gui.getSize()) return -1;
        if (this.guiType.equals(GUIType.SIMPLE)) {
            return getRawSlot();
        } else {
            // Navbar click.
            if (getRawSlot() >= this.gui.getSize() - 9) return -1;
            if (this.gui instanceof DynamicGUI && !((DynamicGUI) this.gui).isPaginated()) {
                return getRawSlot();
            } else {
                return getRawSlot() + 45 * (this.page);
            }
        }
    }

    /**
     * Checks if a {@link GUIButtonClickEvent} was fired from the same click event this event was fired.
     *
     * @return true if a {@link GUIButtonClickEvent} was called.
     */
    public boolean isButtonClick() {
        return this.isButtonClick;
    }
}
