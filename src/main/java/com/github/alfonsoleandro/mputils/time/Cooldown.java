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
package com.github.alfonsoleandro.mputils.time;

import com.github.alfonsoleandro.mputils.MPUtils;
import com.github.alfonsoleandro.mputils.files.YamlFile;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Class used for creating plain cooldowns (for "items", players or functions of any type).
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
     *
     * @param cooldownName The name to be given to this cooldown, used for saving to the cooldown file.
     *                     Suggested cooldownName is "PluginName-CooldownType", i.e: "MPUtils-SendMessage"
     */
    public Cooldown(String cooldownName){
        this.cooldownName = cooldownName;
        this.cooldownYaml = JavaPlugin.getPlugin(MPUtils.class).getCooldownYaml();
    }

    /**
     * Adds an item to the cooldown for a given amount of time.
     *
     * @param itemName The item to add to the cooldown.
     * @param amount The amount of time to add the player to the cooldown for.
     * @param timeUnit The timeunit that the amount represents. See {@link TimeUnit}.
     */
    public void addToCooldown(String itemName, int amount, TimeUnit timeUnit){
        this.cooldownYaml.getAccess().set("cooldowns."+ this.cooldownName +"."+itemName,
                System.currentTimeMillis() +
                java.util.concurrent.TimeUnit.SECONDS.toMillis(TimeUtils.getTotalSeconds((long) amount *timeUnit.getMultiplier())));
        this.cooldownYaml.save(true);
    }

    /**
     * Manually removes an item from the cooldown even if it was not finished yet.
     *
     * @param itemName The item to remove from the cooldown.
     */
    public void removeFromCooldown(String itemName){
        this.cooldownYaml.getAccess().set("cooldowns."+ this.cooldownName +"."+itemName, null);
        this.cooldownYaml.save(true);
    }

    /**
     * Checks if an item is on cooldown.
     * This method will be removed from MPUtils. Please use "{@link #getTimeLeft(String)} {@literal >} 0" instead for
     * checking if an item is on cooldown.
     *
     * @param itemName The player to look for.
     * @return true if the item is on cooldown, and it has not finished.
     */
    @Deprecated
    public boolean isInCooldown(String itemName){
        return getTimeLeft(itemName) > 0;
    }

    /**
     * Gets the time left for an item to leave cooldown (in ticks).
     * As a suggestion, this can be later used on {@link TimeUtils#getTimeString(long)}.
     *
     * @param itemName The player to look for.
     * @return The time left for the item to leave the cooldown or 0 if the item was not in cooldown.
     */
    public long getTimeLeft(String itemName){
        if(!this.cooldownYaml.getAccess().contains("cooldowns."+ this.cooldownName +"."+itemName)) return 0;

        final long timeLeft = this.cooldownYaml.getAccess().getLong("cooldowns."+ this.cooldownName +"."+itemName) - System.currentTimeMillis();

        if(timeLeft <= 0){
            removeFromCooldown(itemName);
            return 0;
        }else{
            return java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(timeLeft)*TimeUnit.SECONDS.getMultiplier();
        }
    }

    /**
     * Removes every item from the cooldown.
     */
    public void removeAll(){
        if(!this.cooldownYaml.getAccess().contains("cooldowns."+ this.cooldownName)) return;
        this.cooldownYaml.getAccess().set("cooldowns."+ this.cooldownName, null);
        this.cooldownYaml.save(true);
    }



}
