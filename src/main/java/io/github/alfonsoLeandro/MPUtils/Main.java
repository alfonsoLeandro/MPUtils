package io.github.alfonsoLeandro.MPUtils;

import io.github.alfonsoLeandro.MPUtils.GUIs.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin {

    final PluginDescriptionFile pdfFile = getDescription();
    final public String version = pdfFile.getVersion();
    //String latestVersion;
    final char color = 'a';
    final String name = ChatColor.translateAlternateColorCodes('&', "&f[&" + color + pdfFile.getName() + "&f]");
    //final String exclamation = "&e&l(&4&l!&e&l)";


    public void send(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', name + msg));
    }


    @Override
    public void onEnable() {
        send("&aEnabled&f. Version: &e" + version);
        send("&fThank you for using my plugin! &" + color + pdfFile.getName() + "&f By " + pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + pdfFile.getWebsite());
        registerEvents();
    }

    @Override
    public void onDisable() {
        send("&cDisabled&f. Version: &e" + version);
        send("&fThank you for using my plugin! &" + color + pdfFile.getName() + "&f By " + pdfFile.getAuthors().get(0));
        send("&fJoin my discord server at &chttps://discordapp.com/invite/ZznhQud");
        send("Please consider subscribing to my yt channel: &c" + pdfFile.getWebsite());
    }

  /*  //
    //updates
    //
    public void updateChecker(){
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "https://api.spigotmc.org/legacy/update.php?resource=").openConnection();
            final int timed_out = 1250;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);
            latestVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (latestVersion.length() <= 7) {
                if(!version.equals(latestVersion)){
                    send(exclamation+" &cThere is a new version available. &e(&7"+latestVersion+"&e)");
                    send(exclamation+" &cDownload it here: &fhttp://bit.ly/");
                }
            }
        } catch (Exception ex) {
            send("&cThere was an error while checking for updates");
        }
    }*/

    //
    //registrar los EVENTOS
    //

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Events(), this);
    }


}
