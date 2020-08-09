package io.github.alfonsoLeandro.MPUtils.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class for creating a GUI of a single page. Check {@link PaginatedGUI} for a paginated GUI.
 */
@SuppressWarnings("unused")
public class SimpleGUI {

    Inventory inv;
    int size;
    HashMap<Integer, ItemStack> items;

    /**
     * Creates an empty non-paginated CHEST type {@link InventoryType#CHEST} inventory for GUI purposes
     * with a title (not colored by default, can be passed colored) and a size.
     *
     * @param title Inventory title.
     * @param size Inventory size.
     */
    public SimpleGUI(String title, int size){
        this.size = size;
        inv = Bukkit.createInventory(null, size, title);
    }

    /**
     * Sets an ItemStack in the given index, check inventory's indexes for reference.
     * Can replace an existing ItemStack if it was in the given index.
     *
     * @param index The index where to put the item.
     * @param item The item to be put.
     */
    public void setItem(int index, ItemStack item){
        if(index >= size || index < 0) return;
        inv.setItem(index, item);
        items.put(index, item);
    }

    /**
     * Adds an ItemStack to the GUI wherever it fits, if it fits.
     *
     * @param item The item to add.
     */
    public void addItem(ItemStack item){
        inv.addItem(item);
        for(int i = 0; i < size; i++){
            if(inv.getItem(i).isSimilar(item)){
                items.put(i, item);
            }
        }
    }

    /**
     * Changes the title of the inv. To do this, it creates a new inventory with the previous size
     * and the new title, then adds the items the previous inventory had.
     *
     * @param title The new title to use.
     */
    public void setTitle(String title){
        inv = Bukkit.createInventory(null, size, title);
        for(int i : items.keySet()){
           inv.setItem(i, items.get(i));
        }

    }

    /**
     * Removes all the items from the inventory.
     */
    public void clearInventory(){
        inv.clear();
        items.clear();
    }

    /**
     * Opens the GUI for a specified player.
     *
     * @param player The player to open the GUI for.
     */
    public void openGUI(Player player){
        if(player == null) return;
        player.openInventory(inv);
        PlayersOnGUIsManager.addPlayer(player.getName(), -1, GUIType.SIMPLE);
    }


}
