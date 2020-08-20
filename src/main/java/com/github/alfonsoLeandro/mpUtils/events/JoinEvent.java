package com.github.alfonsoLeandro.mpUtils.events;

import com.github.alfonsoLeandro.mpUtils.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    final private Main plugin;

    public JoinEvent(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final String exclamation = "&e&l(&4&l!&e&l)";
        final String prefix = "&aMPUtils";

        if(player.isOp() && !plugin.getVersion().equals(plugin.getLatestVersion())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+" "+exclamation+" &4New version available &7(&e"+plugin.getLatestVersion()+"&7)"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+" "+exclamation+" &fhttp://bit.ly/2If90hb") );
        }
    }

}
