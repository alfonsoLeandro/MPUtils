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
package com.github.alfonsoleandro.mputils.listeners;

import com.github.alfonsoleandro.mputils.guis.*;
import com.github.alfonsoleandro.mputils.guis.events.GUIButtonClickEvent;
import com.github.alfonsoleandro.mputils.guis.navigation.GUIButton;
import com.github.alfonsoleandro.mputils.guis.utils.GUIAttributes;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import com.github.alfonsoleandro.mputils.guis.utils.PlayersOnGUIsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Class used to fire custom events and helping {@link PlayersOnGUIsManager}
 *
 * @author alfonsoLeandro
 * @since 1.8.1
 */
public class GUIEvents implements Listener {

    /**
     * The slots of the border of the GUI.
     */
    private final List<Integer> borderGUIButtonSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44);


    /**
     * Method used to fire the {@link GUIButtonClickEvent} when a player clicks on a GUI button and
     * the {@link GUIClickEvent} when a player clicks on a GUI.
     * @param event The event.
     */
    @SuppressWarnings("deprecation") //Support older MPUtils versions
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && PlayersOnGUIsManager.isInGUI(event.getWhoClicked().getName())) {
            int rawSlot = event.getRawSlot();
            GUIAttributes attributes = PlayersOnGUIsManager.getAttributesByPlayer(event.getWhoClicked().getName());
            GUI gui = attributes.getGui();
            //<editor-fold desc="Legacy GUIClickEvent" defaultstate="collapsed">
            GUIClickEvent guiClickEvent = new GUIClickEvent(
                    event.getView(),
                    event.getSlotType(),
                    rawSlot,
                    event.getClick(),
                    event.getAction(),
                    event.getHotbarButton(),
                    attributes.getGuiType().equals(GUIType.PAGINATED) ?
                            com.github.alfonsoleandro.mputils.guis.GUIType.PAGINATED :
                            com.github.alfonsoleandro.mputils.guis.GUIType.SIMPLE,
                    attributes.getPage(),
                    gui);
            //</editor-fold>
            GUIButtonClickEvent guiButtonClickEvent = null;
            if (rawSlot < event.getInventory().getSize()
                    && attributes.getGuiType().equals(GUIType.PAGINATED)
                    && attributes.getGui() instanceof Navigable) {
                int buttonSlot;

                if ((rawSlot > event.getInventory().getSize() - 10 &&
                        rawSlot <= event.getInventory().getSize())) {
                    buttonSlot = rawSlot - (event.getInventory().getSize() - 9);
                    GUIButton clickedButton = ((Navigable) gui).getNavBar().getButtonAt(buttonSlot);
                    //<editor-fold desc="GUIButtonClickEvent" defaultstate="collapsed">
                    guiButtonClickEvent = new GUIButtonClickEvent(
                            event.getView(),
                            event.getSlotType(),
                            rawSlot,
                            event.getClick(),
                            event.getAction(),
                            event.getHotbarButton(),
                            attributes.getPage(),
                            (Navigable) gui,
                            clickedButton,
                            buttonSlot);
                    //</editor-fold>

                } else if (gui instanceof BorderPaginatedGUI) {
                    if (this.borderGUIButtonSlots.contains(rawSlot)) {
                        buttonSlot = this.borderGUIButtonSlots.indexOf(rawSlot) + 10;
                        GUIButton clickedButton = ((Navigable) gui).getNavBar()
                                .getButtonAt(buttonSlot);

                        //<editor-fold desc="GUIButtonClickEvent" defaultstate="collapsed">
                        guiButtonClickEvent = new GUIButtonClickEvent(
                                event.getView(),
                                event.getSlotType(),
                                rawSlot,
                                event.getClick(),
                                event.getAction(),
                                event.getHotbarButton(),
                                attributes.getPage(),
                                (Navigable) gui,
                                clickedButton,
                                buttonSlot);
                        //</editor-fold>
                    }
                }

            }

            //<editor-fold desc="GUIClickEvent" defaultstate="collapsed">
            com.github.alfonsoleandro.mputils.guis.events.GUIClickEvent guiClickEvent2 =
                    new com.github.alfonsoleandro.mputils.guis.events.GUIClickEvent(
                            event.getView(),
                            event.getSlotType(),
                            rawSlot,
                            event.getClick(),
                            event.getAction(),
                            event.getHotbarButton(),
                            attributes.getGuiType(),
                            attributes.getPage(),
                            gui,
                            guiButtonClickEvent != null);
            //</editor-fold>

            //<editor-fold desc="Call events" defaultstate="collapsed">
            Bukkit.getPluginManager().callEvent(guiClickEvent);
            Bukkit.getPluginManager().callEvent(guiClickEvent2);
            if (guiButtonClickEvent != null) Bukkit.getPluginManager().callEvent(guiButtonClickEvent);
            //</editor-fold>

            event.setCancelled(guiClickEvent.isCancelled() || guiClickEvent2.isCancelled()
                    || (guiButtonClickEvent != null && guiButtonClickEvent.isCancelled())
            );
        }
    }

    /**
     * Method used to fire the {@link com.github.alfonsoleandro.mputils.guis.events.GUICloseEvent}
     * @param event The {@link InventoryCloseEvent} to handle.
     */
    @SuppressWarnings("deprecation") //Support older MPUtils versions.
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (PlayersOnGUIsManager.isInGUI(event.getPlayer().getName())) {
            GUIAttributes attributes = PlayersOnGUIsManager.getAttributesByPlayer(event.getPlayer().getName());
            Bukkit.getPluginManager().callEvent(new GUICloseEvent(
                    event.getView(),
                    attributes.getGuiType(),
                    attributes.getPage(),
                    attributes.getGui()));
            Bukkit.getPluginManager().callEvent(new com.github.alfonsoleandro.mputils.guis.events.GUICloseEvent(
                    event.getView(),
                    attributes.getGuiType(),
                    attributes.getPage(),
                    attributes.getGui()));
            PlayersOnGUIsManager.removePlayer(event.getPlayer().getName());
        }
    }
}
