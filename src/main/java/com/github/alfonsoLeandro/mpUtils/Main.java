package com.github.alfonsoLeandro.mpUtils;

import com.github.alfonsoLeandro.mpUtils.events.JoinEvent;
import com.github.alfonsoLeandro.mpUtils.guis.Events;
import com.github.alfonsoLeandro.mpUtils.metrics.Metrics;
import com.github.alfonsoLeandro.mpUtils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Main extends JavaPlugin {

    final PluginDescriptionFile pdfFile = getDescription();
    final public String version = pdfFile.getVersion();
    String latestVersion;
    final char color = 'a';
    final String exclamation = "&e&l(&4&l!&e&l)";


    public static void send(String msg){
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorizeString('&', "&f[&aMPUtils&f] "+msg));
    }


    @Override
    public void onEnable() {
        send("&aEnabled&f. Version: &e" + version);
        send("&fThank you for using my plugin! &" + color + pdfFile.getName() + "&f By " + pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + pdfFile.getWebsite());
        registerEvents();
        updateChecker();
        startMetrics();
    }

    @Override
    public void onDisable() {
        send("&cDisabled&f. Version: &e" + version);
        send("&fThank you for using my plugin! &" + color + pdfFile.getName() + "&f By " + pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + pdfFile.getWebsite());
    }

    public void startMetrics(){
        new Metrics(this, 8825);
    }

    //
    // Update checker
    //
    public void updateChecker(){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=82788").openConnection();
            final int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            latestVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (latestVersion.length() <= 7) {
                if(!version.equals(latestVersion)){
                    send(exclamation+" &cThere is a new version available. &e(&7"+latestVersion+"&e)");
                    send(exclamation+" &cDownload it here: &fhttp://bit.ly/MPUtils");
                }
            }
        } catch (Exception ex) {
            send("&cThere was an error while checking for updates");
        }
    }

    //
    //register Events
    //
    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new JoinEvent(this), this);
    }


    public String getVersion() {
        return version;
    }

    public String getLatestVersion(){
        return latestVersion;
    }
}
