package com.github.alfonsoLeandro.MPUtils.GUIs;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player clicker;
    private final GUIType guiType;
    private final int page;
    private final InventoryClickEvent event;
    private final String guiTags;

    /**
     * Custom event for GUI clicks, called when a player who is being GUI managed by MPUtils clicks a GUI.
     * Should be used for turning pages, interactive GUIs and cancelling {@link InventoryClickEvent}
     *
     * @param clicker The player that clicked the GUI.
     * @param guiType The {@link GUIType} clicked, either {@link GUIType#PAGINATED} or {@link GUIType#SIMPLE}
     * @param page The page the clicker was on when clicking, or -1 if the {@link GUIType} is {@link GUIType#SIMPLE}
     * @param event The actual {@link InventoryClickEvent} fired, for you to modify it at your will.
     */
    public GUIClickEvent(Player clicker, GUIType guiType, int page, InventoryClickEvent event, String guiTags) {
        this.clicker = clicker;
        this.guiType = guiType;
        this.page = page;
        this.event = event;
        this.guiTags = guiTags;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    public Player getClicker() {
        return clicker;
    }

    public GUIType getGuiType() {
        return guiType;
    }

    public int getPage() {
        return page;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }

    public String getGuiTags() {
        return guiTags;
    }

}
