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
package com.github.alfonsoleandro.mputils.time;

import com.github.alfonsoleandro.mputils.MPUtils;
import com.github.alfonsoleandro.mputils.files.YamlFile;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Class used for creating cooldowns
 */
public class Cooldown {

    /**
     * YAMLFile object containing cooldowns.
     */
    private final YamlFile cooldownYaml;
    /**
     * Cooldown name for this object, used for creating more than one cooldown, for different purposes.
     * i.e: send a message cooldown and open a chest cooldown.
     */
    private final String cooldownName;


    /**
     * Creates a cooldown object.
     * When creating a new cooldown the HashMap is also new so players in another cooldown do not
     * have nothing to do with the new cooldown.
     *
     * @param cooldownName The name to be given to this cooldown, used for saving to the cooldown file.
     *                     Suggested cooldownName is "PluginName-CooldownType", i.e: "MPUtils-SendMessage"
     */
    public Cooldown(String cooldownName){
        this.cooldownName = cooldownName;
        this.cooldownYaml = JavaPlugin.getPlugin(MPUtils.class).getCooldownYaml();
    }

    /**
     * Adds a player to the cooldown for a given amount of time.
     *
     * @param playerName The player to add.
     * @param amount The amount of time to add the player to the cooldown for.
     * @param timeUnit The timeunit that the amount represents. See {@link TimeUnit}.
     */
    public void addToCooldown(String playerName, int amount, TimeUnit timeUnit){
        cooldownYaml.getAccess().set("cooldowns."+cooldownName+"."+playerName,
                System.currentTimeMillis() +
                java.util.concurrent.TimeUnit.SECONDS.toMillis(TimeUtils.getTotalSeconds((long) amount *timeUnit.getMultiplier())));
        cooldownYaml.save();
    }

    /**
     * Manually removes a player from the cooldown even if it was not finished yet.
     *
     * @param playerName The player to remove from the cooldown.
     */
    public void removeFromCooldown(String playerName){
        cooldownYaml.getAccess().set("cooldowns."+cooldownName+"."+playerName, null);
        cooldownYaml.save();
    }

    /**
     * Checks if a player is on cooldown.
     * This method will be removed from MPUtils. Please use "{@link #getTimeLeft(String)} {@literal >} 0" instead for
     * checking if a player is on cooldown.
     *
     * @param playerName The player to look for.
     * @return true if the player is on cooldown and it has not finished.
     */
    @Deprecated
    public boolean isInCooldown(String playerName){
        return getTimeLeft(playerName) > 0;
    }

    /**
     * Gets the time left for a player to leave cooldown (in ticks).
     * As a suggestion, this can be later used on {@link TimeUtils#getTimeString(long)}.
     *
     * @param playerName The player to look for.
     * @return The time left for the player to leave the cooldown or 0 if the player was not in cooldown.
     */
    public long getTimeLeft(String playerName){
        if(!cooldownYaml.getAccess().contains("cooldowns."+cooldownName+"."+playerName)) return 0;

        final long timeLeft = cooldownYaml.getAccess().getLong("cooldowns."+cooldownName+"."+playerName) - System.currentTimeMillis();

        if(timeLeft <= 0){
            removeFromCooldown(playerName);
            return 0;
        }else{
            return timeLeft*20000;
        }


    }



}
