package com.github.alfonsoLeandro.mpUtils;

import com.github.alfonsoLeandro.mpUtils.events.JoinEvent;
import com.github.alfonsoLeandro.mpUtils.files.YamlFile;
import com.github.alfonsoLeandro.mpUtils.guis.Events;
import com.github.alfonsoLeandro.mpUtils.metrics.Metrics;
import com.github.alfonsoLeandro.mpUtils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Main extends JavaPlugin {

    /**
     * This plugin's plugin.yml file.
     */
    final private PluginDescriptionFile pdfFile = getDescription();
    /**
     * The version string from the plugin.yml file.
     */
    final private  String version = pdfFile.getVersion();
    /**
     * The latest available version on spigot.
     */
    private String latestVersion;
    /**
     * YamlFile object used for storing the {@link FileConfiguration} object for the config file and the file itself.
     */
    private YamlFile configYaml;

    /**
     * Sends a message to the console with colors and prefix.
     * @param msg The message to be sent.
     */
    private void send(String msg){
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorizeString('&', "&f[&aMPUtils&f] "+msg));
    }


    @Override
    public void onEnable() {
        send("&aEnabled&f. Version: &e" + version);
        send("&fThank you for using my plugin! &a" + pdfFile.getName() + "&f By " + pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + pdfFile.getWebsite());
        registerConfig();
        registerEvents();
        updateChecker();
        startMetrics();
    }

    @Override
    public void onDisable() {
        send("&cDisabled&f. Version: &e" + version);
        send("&fThank you for using my plugin! &a" + pdfFile.getName() + "&f By " + pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + pdfFile.getWebsite());
    }

    /**
     * Registers the config file for this plugin.
     */
    private void registerConfig(){
        configYaml = new YamlFile(this, "config.yml");
    }

    /**
     * Tries to start the metrics system.
     */
    private void startMetrics(){
        if(configYaml.getAccess().getBoolean("config.metrics enabled")) {
            new Metrics(this, 8825);
        }else{
            send("&cMetrics are disabled");
            send("&cPlease consider setting &ametrics enabled &cto &atrue &cin config");
        }
    }

    /**
     * Checks for updates using the spigot api.
     */
    private void updateChecker(){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=82788").openConnection();
            final int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            latestVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (latestVersion.length() <= 7) {
                if(!version.equals(latestVersion)){
                    String exclamation = "&e&l(&4&l!&e&l)";
                    send(exclamation +" &cThere is a new version available. &e(&7"+latestVersion+"&e)");
                    send(exclamation +" &cDownload it here: &fhttp://bit.ly/MPUtils");
                }
            }
        } catch (Exception ex) {
            send("&cThere was an error while checking for updates");
        }
    }

    /**
     * Registers every event listener for this plugin.
     */
    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new JoinEvent(this), this);
    }

    /**
     * Gets this plugin's current installed version.
     * @return The version String.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the latest available version from spigot.
     * @return The latest available version or null if the updateChecker failed.
     */
    public String getLatestVersion(){
        return latestVersion;
    }
}
