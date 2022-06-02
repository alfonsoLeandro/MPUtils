package com.github.alfonsoleandro.mputils.sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SoundUtils {

    /**
     * Plays the given sound for the given player.
     * @param player The player to play the sound for.
     * @param soundSettings The sound settings to use.
     */
    public static void playSound(Player player, SoundSettings soundSettings){
        player.playSound(player.getLocation(),
                soundSettings.getSound(),
                soundSettings.getVolume(),
                soundSettings.getPitch());
    }

    /**
     * Plays the given sound in the given location for every player online.
     * @param location The location to play the sound at.
     * @param soundSettings The sound settings to use.
     */
    public static void playSound(Location location, SoundSettings soundSettings){
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(location,
                    soundSettings.getSound(),
                    soundSettings.getVolume(),
                    soundSettings.getPitch());
        }
    }

    /**
     * Plays the given sound for every player online in each player's location.
     * @param soundSettings The sound settings to use.
     */
    public static void playSoundForEveryPlayer(SoundSettings soundSettings){
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(),
                    soundSettings.getSound(),
                    soundSettings.getVolume(),
                    soundSettings.getPitch());
        }
    }


}
