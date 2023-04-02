/*
Copyright (c) 2023 Leandro Alfonso

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
package com.github.alfonsoleandro.mputils.sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Utility methods related to sounds.
 *
 * @author alfonsoLeandro
 * @since 1.10.0
 */
public class SoundUtils {

    /**
     * Plays the given sound for the given player.
     *
     * @param player        The player to play the sound for.
     * @param soundSettings The sound settings to use.
     */
    public static void playSound(Player player, SoundSettings soundSettings) {
        player.playSound(player.getLocation(),
                soundSettings.getSound(),
                soundSettings.getVolume(),
                soundSettings.getPitch());
    }

    /**
     * Plays the given sound in the given location for every player online.
     *
     * @param location      The location to play the sound at.
     * @param soundSettings The sound settings to use.
     */
    public static void playSound(Location location, SoundSettings soundSettings) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(location,
                    soundSettings.getSound(),
                    soundSettings.getVolume(),
                    soundSettings.getPitch());
        }
    }

    /**
     * Plays the given sound for every player online in each player's location.
     *
     * @param soundSettings The sound settings to use.
     */
    public static void playSoundForEveryPlayer(SoundSettings soundSettings) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(),
                    soundSettings.getSound(),
                    soundSettings.getVolume(),
                    soundSettings.getPitch());
        }
    }


}
