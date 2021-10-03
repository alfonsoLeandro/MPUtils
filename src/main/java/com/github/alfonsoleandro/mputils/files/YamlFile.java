package com.github.alfonsoleandro.mputils.files;

import com.google.common.base.Strings;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * YamlFile by OcZi (https://github.com/OcZi). With the permission given to MasterPlugins for using this class.
 *
 * @author OcZi
 */
public class YamlFile {
    /**
     * Your plugin main instance.
     */
    private final JavaPlugin plugin;
    /**
     * String,String map used for refilling nodes.
     * @see #refillNodes()
     */
    private final Map<String, String> mapRefill;
    /**
     * The {@link FileConfiguration} object for this YamlFile object.
     */
    private final FileConfiguration fileConfig;
    /**
     * The file name in your resources' folder.
     */
    private String fileName;
    /**
     * The file located in your plugin's data folder inside the server.
     */
    private final File file;

    /**
     * YamlFile constructor.
     * @param plugin Your plugin's main instance.
     * @param fileName The file name to look for on your resources folder and the file name for the final file in
     *                 your plugin's data folder in the server.
     */
    public YamlFile(JavaPlugin plugin,
                    String fileName) {
        this(plugin, fileName, null);
    }


    /**
     * YamlFile constructor.
     * @param plugin Your plugin's main instance.
     * @param fileName The file name to look for on your resources folder and the file name for the final file in
     *                 your plugin's data folder in the server.
     * @param mapRefill String, String map used for adding default values not set in the default file.
     */
    public YamlFile(JavaPlugin plugin,
                    String fileName,
                    Map<String, String> mapRefill) {
        this.plugin = plugin;
        this.mapRefill = mapRefill;
        this.file = new File(plugin.getDataFolder(), fileName);
        this.fileConfig = new YamlConfiguration();
        this.fileName = fileName;
        saveDefault();
        loadFileConfiguration();

        if (mapRefill != null)  { refillNodes(); }
    }

    /**
     * Loads the {@link FileConfiguration} object.
     */
    public void loadFileConfiguration() {
        try {
            if (Strings.isNullOrEmpty(fileName))
                throw new NullPointerException(
                        "File name is empty or null.");
            if (!fileName.endsWith(".yml")) {
                fileName += ".yml";
            }

            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Fills the config file with some given keys and values.
     */
    public void refillNodes() {
        for (Map.Entry<String, String> mapEntry : mapRefill.entrySet()) {
            if (!fileConfig.isSet(mapEntry.getKey())) {
                fileConfig.set(mapEntry.getKey(), mapEntry.getValue());
            }
        }
        save(true);
    }

    /**
     * Saves the {@link FileConfiguration} object to the file in your plugin's data folder.
     * @deprecated Please use {@link #save(boolean)} instead. Allows you to decide if you want to save the
     * file synchronously or asynchronously.
     */
    @Deprecated
    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the {@link FileConfiguration} object to the file in your plugin's data folder.
     * @param async Whether to save this file synchronously or asynchronously.
     */
    public void save(boolean async) {
        if(async){
            new BukkitRunnable(){
                public void run(){
                    try {
                        fileConfig.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(plugin);
        }else{
            try {
                fileConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves the default values from your resources' file.
     */
    public void saveDefault() {
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    /**
     * Gets the file in your plugin's data folder.
     * @return The File object.
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the fileName you first entered when creating this YamlFile object.
     * @return the file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the {@link FileConfiguration} object of this YamlFile object.
     * @return The FileConfiguration object.
     */
    public FileConfiguration getAccess() {
        return fileConfig;
    }
}