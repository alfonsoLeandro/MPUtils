package com.github.alfonsoLeandro.MPUtils.GUIs;

import java.util.HashMap;

/**
 * Class for managing which players have GUIs opened and what page they are on.
 */
final class PlayersOnGUIsManager {

    static HashMap<String, GUIAtributes> players = new HashMap<>();

    /**
     * get the page where the player is at in a GUI, if they are in a GUI.
     *
     * @param playerName The name of the player to look for.
     * @return the page the player is in if it is a paginated GUI, -1 if it is not paginated or null if that player is not in a GUI.
     */
    public static GUIAtributes getAtributesByPlayer(String playerName){
        return players.get(playerName);
    }


    /**
     * Adds a player to the GUIManager
     *
     * @param playerName The name of the player to add.
     * @param pageNumber The page of the GUI the player is on, or -1 if its not a paginated GUI.
     */
    public static void addPlayer(String playerName, int pageNumber, GUIType guiType){
        players.remove(playerName);
        players.put(playerName, new GUIAtributes(pageNumber, guiType));
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
