package com.github.alfonsoLeandro.mpUtils.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUI {

    /**
     * The actual inventory, where the GUI is supposed to go in.
     */
    protected Inventory inventory;
    /**
     * Some extra tags you may add to differentiate between GUIs.
     */
    final protected String guiTags;
    /**
     * The size for the GUI. In case of a {@link PaginatedGUI} this is the size for each page.
     */
    protected int guiSize;


    protected GUI(String title, int size, String guiTags) {
        if(size > 54) size = 54;
        if(size % 9 != 0) size = (int) Math.floor(size / 9.0);
        if(size <= 9) size = 18;

        this.guiSize = size;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.guiTags = guiTags;
    }

    /**
     * Opens the GUI for the given player. In case the GUI is an instance of {@link PaginatedGUI}
     * this will open the GUI in the page number 0.
     * @param player The player to open the GUI for.
     */
    public void openGUI(Player player){
        if(player == null) return;
        player.openInventory(inventory);
        PlayersOnGUIsManager.addPlayer(player.getName(), -1, GUIType.SIMPLE, guiTags, this);
    }

    /**
     * Clears the inventory for this GUI object.
     */
    public abstract void clearInventory();

    /**
     * Adds an item to the item list.
     * @param item The item to add.
     */
    public abstract void addItem(ItemStack item);

    /**
     * Get the unique tags for this GUI.
     * @return The GuiTags String for this GUI.
     */
    public String getGuiTags(){
        return this.guiTags;
    }
}
