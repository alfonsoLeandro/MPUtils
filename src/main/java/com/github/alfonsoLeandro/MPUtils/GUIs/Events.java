package com.github.alfonsoLeandro.MPUtils.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Class used to fire custom events and helping {@link PlayersOnGUIsManager}
 */
public class Events implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player && PlayersOnGUIsManager.isInGUI(event.getWhoClicked().getName())){
            GUIAtributes atributes = PlayersOnGUIsManager.getAtributesByPlayer(event.getWhoClicked().getName());
            Bukkit.getPluginManager().callEvent(new GUIClickEvent((Player)event.getWhoClicked(), atributes.getGuiType(), atributes.getPage(), event));
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        PlayersOnGUIsManager.removePlayer(event.getPlayer().getName());
    }
}
