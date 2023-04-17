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
package com.github.alfonsoleandro.mputils;

import com.github.alfonsoleandro.mputils.files.YamlFile;
import com.github.alfonsoleandro.mputils.listeners.GUIEvents;
import com.github.alfonsoleandro.mputils.listeners.JoinEvent;
import com.github.alfonsoleandro.mputils.string.StringUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * MPUtils' main class. Nothing useful here.
 *
 * @author alfonsoLeandro
 * @since 0.9.0
 */
public final class MPUtils extends JavaPlugin {

    /**
     * This plugin's plugin.yml file.
     */
    final private PluginDescriptionFile pdfFile = getDescription();
    /**
     * The version string from the plugin.yml file.
     */
    final private String version = this.pdfFile.getVersion();
    /**
     * YamlFile object used for storing the {@link FileConfiguration} object for the config file and the file itself.
     */
    private YamlFile configYaml;
    /**
     * YamlFile object used for storing the {@link FileConfiguration} object for the cooldown file and the file itself.
     */
    private YamlFile cooldownYaml;
    /**
     * The latest available version on spigot.
     */
    private String latestVersion;

    /**
     * Sends a message to the console with colors and prefix.
     *
     * @param msg The message to be sent.
     */
    private void send(String msg) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorizeString('&', "&f[&aMPUtils&f] " + msg));
    }

    /**
     * Plugin enable logic.
     */
    @Override
    public void onEnable() {
        send("&aEnabled&f. Version: &e" + this.version);
        send("&fThank you for using my plugin! &a" + this.pdfFile.getName() + "&f By " + this.pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + this.pdfFile.getWebsite());
        registerConfig();
        registerCooldown();
        registerEvents();
        updateChecker();
        startMetrics();
    }

    /**
     * Plugin disable logic.
     */
    @Override
    public void onDisable() {
        send("&cDisabled&f. Version: &e" + this.version);
        send("&fThank you for using my plugin! &a" + this.pdfFile.getName() + "&f By " + this.pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + this.pdfFile.getWebsite());
    }

    /**
     * Registers the config file for this plugin.
     */
    private void registerConfig() {
        this.configYaml = new YamlFile(this, "config.yml");
    }

    /**
     * Registers the cooldown file for this plugin.
     */
    private void registerCooldown() {
        this.cooldownYaml = new YamlFile(this, "cooldowns.yml");
    }

    /**
     * Tries to start the metrics system.
     */
    private void startMetrics() {
        if (this.configYaml.getAccess().getBoolean("config.metrics enabled")) {
            new Metrics(this, 8825);
        } else {
            send("&cMetrics are disabled");
            send("&cPlease consider setting &ametrics enabled &cto &atrue &cin config");
        }
    }

    /**
     * Checks for updates using the spigot api.
     */
    private void updateChecker() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=82788").openConnection();
            final int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            this.latestVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (this.latestVersion.length() <= 7) {
                if (!this.version.equals(this.latestVersion)) {
                    String exclamation = "&e&l(&4&l!&e&l)";
                    send(exclamation + " &cThere is a new version available. &e(&7" + this.latestVersion + "&e)");
                    send(exclamation + " &cDownload it here: &fhttps://bit.ly/MPUtils");
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
        pm.registerEvents(new GUIEvents(), this);
        pm.registerEvents(new JoinEvent(this), this);
    }

    /**
     * Gets this plugin's current installed version.
     *
     * @return The version String.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Gets the latest available version from spigot.
     *
     * @return The latest available version or null if the updateChecker failed.
     */
    public String getLatestVersion() {
        return this.latestVersion;
    }

    /**
     * Gets the cooldown YAMLFile object.
     *
     * @return The YAMLFile object containing the cooldown's File and FileConfiguration objects.
     */
    public YamlFile getCooldownYaml() {
        return this.cooldownYaml;
    }
}
