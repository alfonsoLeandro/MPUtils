package com.github.alfonsoLeandro.mpUtils.guis;

import java.util.HashMap;

/**
 * Class for managing which players have GUIs opened and what page they are on.
 */
public final class PlayersOnGUIsManager {

    private static final HashMap<String, GUIAtributes> players = new HashMap<>();


    /**
     * get the page where the player is at in a GUI, if they are in a GUI.
     *
     * @param playerName The name of the player to look for.
     * @return the page the player is in if it is a paginated GUI, -1 if it is not paginated or null if that player is not in a GUI.
     */
    public static GUIAtributes getAttributesByPlayer(String playerName){
        return players.get(playerName);
    }


    /**
     * Adds a player to the GUIManager
     *
     * @param playerName The name of the player to add.
     * @param pageNumber The page of the GUI the player is on, or -1 if its not a paginated GUI.
     * @param guiType The GUI type the player is currently on. Either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     * @param guiTags Any tags passed to the GUI instance in order to recognize it. Then passed to the {@link GUIClickEvent}.
     * @param gui The GUI instance used to then pass it to {@link GUIClickEvent}.
     */
    public static void addPlayer(String playerName, int pageNumber, GUIType guiType, String guiTags, Object gui){
        players.remove(playerName);
        players.put(playerName, new GUIAtributes(pageNumber, guiType, guiTags, gui));
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