package com.github.alfonsoLeandro.mpUtils.time;

import java.util.HashMap;

/**
 * Class used for creating cooldowns
 */
public class Cooldown {

    /**
     * HashMap used for storing each player name with their corresponding cooldown ending time.
     */
    private final HashMap<String, Long>  playersOnCooldown;

    /**
     * Creates a cooldown object, you can store more than one player per cooldown.
     * When creating a new cooldown the HashMap is also new so players in another cooldown do not
     * have nothing to do with the new cooldown.
     */
    public Cooldown(){
        this.playersOnCooldown = new HashMap<>();
    }

    /**
     * Adds a player to the cooldown for x amount of seconds.
     *
     * @param playerName The player to add.
     * @param seconds The time in seconds left for this player's cooldown to end.
     */
    public void addToCooldown(String playerName, int seconds){
        playersOnCooldown.put(playerName, System.currentTimeMillis()+(seconds*1000));
    }

    /**
     * Manually removes a player from the cooldown even if it was not finished yet.
     *
     * @param playerName The player to remove from the cooldown.
     */
    public void removeFromCooldown(String playerName){
        playersOnCooldown.remove(playerName);
    }

    /**
     * Checks if a player is on cooldown.
     *
     * @param playerName The player to look for.
     * @return true if the player is on cooldown and it has not finished.
     */
    public boolean isOnCooldown(String playerName){
        if(playersOnCooldown.containsKey(playerName)){
            if(playersOnCooldown.get(playerName) > System.currentTimeMillis()){
                return true;
            }else{
                playersOnCooldown.remove(playerName);
                return  false;
            }
        }else{
            return false;
        }
    }

    /**
     * Gets the time left for a player to leave cooldown.
     *
     * @param playerName The player to look for.
     * @return The time left for the player to leave the cooldown.
     */
    public long getTimeLeft(String playerName){

        if(playersOnCooldown.containsKey(playerName)) return 0;

        final long timeLeft = playersOnCooldown.get(playerName) - System.currentTimeMillis();

        if(timeLeft <= 0){
            playersOnCooldown.remove(playerName);
            return 0;
        }else{
            return timeLeft/1000;
        }


    }


}
