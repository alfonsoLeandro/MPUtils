package com.github.alfonsoleandro.mputils.guis.events;

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

public class GUIButtonClickEvent extends GUIClickEvent {

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
     * Custom event for GUIButton clicks, called when a player who is being GUI managed by MPUtils clicks a button
     * inside a GUI's navigation bar.
     * Should be used for turning pages, interactive GUIs and cancelling the {@link InventoryClickEvent}.
     *
     * @param view {@link InventoryClickEvent}'s InventoryView.
     * @param type {@link InventoryClickEvent}'s SlotType.
     * @param slot {@link InventoryClickEvent}'s clicked slot.
     * @param click {@link InventoryClickEvent}'s ClickType.
     * @param action {@link InventoryClickEvent}'s InventoryAction.
     * @param key {@link InventoryClickEvent}'s hotbar key pressed (if any).
     * @param guiType The {@link GUIType} clicked, either {@link GUIType#PAGINATED} or {@link GUIType#SIMPLE}
     * @param page The page the clicker was on when clicking, or -1 if the {@link GUIType} is {@link GUIType#SIMPLE}
     * @param gui The PaginatedGUI object.
     * @param clickedButton The button that was involved in this event.
     * @param navBarSlot The slot in which the clicked button was in relative to the navigation bar.
     * @see NavigationBar
     */
    public GUIButtonClickEvent(InventoryView view,
                               InventoryType.SlotType type,
                               int slot,
                               ClickType click,
                               InventoryAction action,
                               int key,
                               GUIType guiType,
                               int page,
                               PaginatedGUI gui,
                               GUIButton clickedButton,
                               int navBarSlot){
        super(view, type, slot, click, action, key, guiType, page, gui);
        this.clickedButton = clickedButton;
        this.navBarSlot = navBarSlot;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    /**
     * Gets the page the player was standing on when clicking the GUI.
     *
     * @return The page number the player was standing on when clicking the GUI.
     */
    public int getPage() {
        return page;
    }

    /**
     * Gets the instance of the GUI object so you can get the navBar items.
     *
     * @return Instance of the object {@link PaginatedGUI}.
     */
    public PaginatedGUI getGui() {
        return (PaginatedGUI) gui;
    }

    /**
     * Gets the button involved in this event.
     * @return The button responsible of triggering this event.
     */
    public GUIButton getClickedButton(){
        return this.clickedButton;
    }


    /**
     * Gets the slot of the button that was clicked.
     * Can be used to get the button using {@link NavigationBar#getButtonAt(int)}.
     * @return The navigation bar slot the button was placed.
     */
    public int getNavBarSlot(){
        return this.navBarSlot;
    }
}
