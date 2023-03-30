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

import com.github.alfonsoleandro.mputils.MPUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * {@link PlayerJoinEvent} listener used for notifying server operators whenever there
 * is a new MPUtils version available.
 */
public class JoinEvent implements Listener {

    private final MPUtils plugin;

    public JoinEvent(MPUtils plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final String exclamation = "&e&l(&4&l!&e&l)";
        final String prefix = "&f[&aMPUtils&f]";

        if(player.isOp() && !this.plugin.getVersion().equals(this.plugin.getLatestVersion())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+" "+exclamation+" &4New version available &7(&e"+ this.plugin.getLatestVersion()+"&7)"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+" "+exclamation+" &fhttps://bit.ly/MPUtils") );
        }
    }

}
