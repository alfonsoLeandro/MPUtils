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
package com.github.alfonsoleandro.mputils.guis.utils;

import com.github.alfonsoleandro.mputils.guis.GUI;
import com.github.alfonsoleandro.mputils.guis.events.GUIClickEvent;

import java.util.HashMap;

/**
 * Class for managing which players have GUIs opened and what page they are on.
 */
public final class PlayersOnGUIsManager {

    /**
     * A map of player names that are being managed by this class.
     */
    private static final HashMap<String, GUIAttributes> players = new HashMap<>();


    /**
     * get the page where the player is at in a GUI, if they are in a GUI.
     *
     * @param playerName The name of the player to look for.
     * @return the page the player is in if it is a paginated GUI, -1 if it is not paginated or null if that player is not in a GUI.
     */
    public static GUIAttributes getAttributesByPlayer(String playerName){
        return players.get(playerName);
    }


    /**
     * Adds a player to the GUIManager
     *
     * @param playerName The name of the player to add.
     * @param pageNumber The page of the GUI the player is on, or -1 if its not a paginated GUI.
     * @param guiType The GUI type the player is currently on. Either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     * @param gui The GUI instance used to then pass it to {@link GUIClickEvent}.
     */
    public static void addPlayer(String playerName, int pageNumber, GUIType guiType, GUI gui){
        players.remove(playerName);
        players.put(playerName, new GUIAttributes(pageNumber, guiType, gui));
    }

    /**
     * Removes a player from the GUIManager.
     *
     * @param playerName The player to look for.
     */
    public static void removePlayer(String playerName){
        players.remove(playerName);
    }

    /**
     * Check if a player is in a GUI managed by this GUIManager.
     *
     * @param playerName The name of the player to look for.
     * @return true if there is a player with that name being managed by this manager.
     */
    public static boolean isInGUI(String playerName){
        return players.containsKey(playerName);
    }


}
