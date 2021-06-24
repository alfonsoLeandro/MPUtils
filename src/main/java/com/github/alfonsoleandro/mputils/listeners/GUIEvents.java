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
package com.github.alfonsoleandro.mputils.listeners;

import com.github.alfonsoleandro.mputils.guis.GUIAttributes;
import com.github.alfonsoleandro.mputils.guis.GUIClickEvent;
import com.github.alfonsoleandro.mputils.guis.GUICloseEvent;
import com.github.alfonsoleandro.mputils.guis.PlayersOnGUIsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Class used to fire custom events and helping {@link PlayersOnGUIsManager}
 */
public class GUIEvents implements Listener {


    @EventHandler (priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player && PlayersOnGUIsManager.isInGUI(event.getWhoClicked().getName())){
            GUIAttributes attributes = PlayersOnGUIsManager.getAttributesByPlayer(event.getWhoClicked().getName());
            GUIClickEvent guiClickEvent = new GUIClickEvent(
                    event.getView(),
                    event.getSlotType(),
                    event.getSlot(),
                    event.getClick(),
                    event.getAction(),
                    event.getHotbarButton(),
                    attributes.getGuiType(),
                    attributes.getPage(),
                    attributes.getGui().getGuiTags(),
                    attributes.getGui());
            Bukkit.getPluginManager().callEvent(guiClickEvent);
            event.setCancelled(guiClickEvent.isCancelled());
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(PlayersOnGUIsManager.isInGUI(event.getPlayer().getName())) {
            GUIAttributes attributes = PlayersOnGUIsManager.getAttributesByPlayer(event.getPlayer().getName());
            Bukkit.getPluginManager().callEvent(new GUICloseEvent(
                    event.getView(),
                    attributes.getGuiType(),
                    attributes.getPage(),
                    attributes.getGui().getGuiTags(),
                    attributes.getGui()));
            PlayersOnGUIsManager.removePlayer(event.getPlayer().getName());
        }
    }
}
