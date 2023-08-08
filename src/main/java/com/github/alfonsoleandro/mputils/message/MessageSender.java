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
package com.github.alfonsoleandro.mputils.message;

import com.github.alfonsoleandro.mputils.files.YamlFile;
import com.github.alfonsoleandro.mputils.reloadable.Reloadable;
import com.github.alfonsoleandro.mputils.reloadable.ReloaderPlugin;
import com.github.alfonsoleandro.mputils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Messages manager. Should manage every message a plugin can send. Includes several message sending methods.
 *
 * @param <E> The Enum supposed to contain every configurable message included in a configuration file.
 * @author alfonsoLeandro
 * @since 1.10.0
 */
public class MessageSender<E extends MessageEnum> extends Reloadable {

    /**
     * A map containing every configurable message.
     */
    private final Map<E, String> messages = new HashMap<>();
    /**
     * An array containing every value inside an enum. (enum#values).
     */
    private final E[] messagesEnumValues;
    /**
     * The plugin using this manager's main class.
     */
    private final ReloaderPlugin plugin;
    /**
     * The path where the prefix is located (if any) inside the same file that contains the messages.
     */
    private final String prefixPath;
    /**
     * The {@link YamlFile} object that will contain every message listed in the enum.
     */
    private YamlFile messagesYamlFile;
    /**
     * A string that goes before every message.
     */
    private String prefix;

    /**
     * Creates a new instance of the message sender.
     *
     * @param plugin             The plugin using this manager's main class instance.
     * @param messagesEnumValues The values from the enum containing the messages, each
     *                           of these values will be used later to load the messages from the given yaml file
     *                           after the given path. Keep in mind that "_" will be replaced for " " and the string
     *                           will be in lower case.
     * @param messagesYamlFile   The YamlFile where the messages will be taken from.
     * @param prefixPath         The path where the prefix (string before every message) will be found inside the given YamlFile.
     *                           Can be null (messages will not have a prefix by default).
     */
    public MessageSender(ReloaderPlugin plugin, E[] messagesEnumValues,
                         YamlFile messagesYamlFile, @Nullable String prefixPath) {
        super(plugin);
        this.plugin = plugin;
        this.messagesEnumValues = messagesEnumValues;
        this.messagesYamlFile = messagesYamlFile;
        this.prefixPath = prefixPath;
        loadMessages();
    }

    /**
     * Creates a new instance of the message sender.
     *
     * @param plugin             The plugin using this manager's main class instance.
     * @param messagesEnumValues The values from the enum containing the messages, each
     *                           of these values will be used later to load the messages from the given yaml file
     *                           after the given path. Keep in mind that "_" will be replaced for " " and the string
     *                           will be in lower case.
     * @param messagesYamlFile   The YamlFile where the messages will be taken from.
     **/
    public MessageSender(@NotNull ReloaderPlugin plugin, @NotNull E[] messagesEnumValues,
                         @NotNull YamlFile messagesYamlFile) {
        this(plugin, messagesEnumValues, messagesYamlFile, null);
    }


    /**
     * Loads every message and the prefix, if the prefix path has been specified.
     */
    private void loadMessages() {
        FileConfiguration messages = this.messagesYamlFile.getAccess();
        this.prefix = this.prefixPath == null ? null : messages.getString(this.prefixPath);
        this.messages.clear();

        for (E message : this.messagesEnumValues) {
            this.messages.put(message,
                    messages.getString(message.getPath()));
        }
    }

    /**
     * Sends a string to the given CommandSender.
     *
     * @param receiver The intended receiver for the message.
     * @param msg      The String to send.
     */
    public void send(@NotNull CommandSender receiver, @NotNull String msg) {
        receiver.sendMessage(StringUtils.colorizeString((this.prefix == null ? "" : this.prefix + " ") + msg));
    }

    /**
     * Sends a string to the console.
     *
     * @param msg The string to send.
     */
    public void send(@NotNull String msg) {
        send(Bukkit.getConsoleSender(), msg);
    }

    /**
     * Sends a message to the given CommandSender.
     *
     * @param receiver     The intended receiver for the message.
     * @param message      The message to send.
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     */
    public void send(@NotNull CommandSender receiver, @NotNull E message, String... replacements) {
        send(receiver, getString(message, replacements));
    }

    /**
     * Sends a message to the given CommandSender.
     *
     * @param receivers    The list of intended receivers for the message.
     * @param message      The message to send.
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     */
    public void send(@NotNull List<CommandSender> receivers, @NotNull E message, String... replacements) {
        for (CommandSender receiver : receivers) {
            send(receiver, getString(message, replacements));
        }
    }

    /**
     * Sends a message to every player online and the console.
     *
     * @param excluded     A player to exclude from this message.
     * @param message      The message to send.
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     */
    public void broadcast(@Nullable Player excluded, @NotNull E message, String... replacements) {
        String msg = getString(message, replacements);

        for (Player toSend : Bukkit.getOnlinePlayers()) {
            if (toSend.equals(excluded)) continue;
            send(toSend, msg);
        }
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorizeString((this.prefix == null ? "" : this.prefix + " ") + msg));
    }

    /**
     * Sends a message to every player online and the console.
     *
     * @param excluded     A list of players to exclude from this message.
     * @param message      The message to send.
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     */
    public void broadcast(@Nullable List<Player> excluded, @NotNull E message, String... replacements) {
        String msg = getString(message, replacements);

        for (Player toSend : Bukkit.getOnlinePlayers()) {
            if (excluded != null && excluded.contains(toSend)) continue;
            send(toSend, msg);
        }
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorizeString((this.prefix == null ? "" : this.prefix + " ") + msg));
    }

    /**
     * Sends a title and subtitle to a player.
     *
     * @param player       The player that will be receiving this title and subtitle.
     * @param title        The title to send (set to null to not send title).
     * @param subtitle     The subtitle to send (set to null to not send subtitle).
     * @param stay         The amount of time the title
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     */
    public void title(@NotNull Player player, @Nullable E title, @Nullable E subtitle, int stay, String... replacements) {
        String ttl = null;
        if (title != null) {
            ttl = getString(title, replacements);
        }
        String sub = null;
        if (subtitle != null) {
            sub = getString(subtitle, replacements);
        }
        player.sendTitle(ttl == null ? "" : StringUtils.colorizeString(ttl),
                sub == null ? "" : StringUtils.colorizeString(sub),
                4,
                stay,
                4);
    }

    /**
     * Sends a title and subtitle to a player.
     *
     * @param player       The player that will be receiving this title and subtitle.
     * @param title        The title to send (set to "" or null to not send title).
     * @param subtitle     The subtitle to send (set to "" or null to not send subtitle).
     * @param stay         The amount of time the title
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     */
    public void title(@NotNull Player player, @Nullable String title, @Nullable String subtitle, int stay, String... replacements) {
        if (title != null) {
            for (int i = 0; i < replacements.length; i++) {
                title = title.replace(replacements[i], replacements[++i]);
            }
        }
        if (subtitle != null) {
            for (int i = 0; i < replacements.length; i++) {
                subtitle = subtitle.replace(replacements[i], replacements[++i]);
            }
        }
        player.sendTitle(title == null ? "" : StringUtils.colorizeString(title),
                subtitle == null ? "" : StringUtils.colorizeString(subtitle),
                4,
                stay,
                4);
    }

    /**
     * Gets the string that goes with the given enum value ("message").
     *
     * @param message      The enum value.
     * @param replacements The string to replace from the message and its replacements in the following format:
     *                     "string1", "replacement1", "string2", replacement2,... , "stringN", "replacementN".
     * @return The string that goes with the given message with the given strings replaced.
     */
    public String getString(E message, String... replacements) {
        String msg = this.messages.get(message);
        if (msg == null) msg = message.getDefault();
        for (int i = 0; i < replacements.length; i++) {
            msg = msg.replace(replacements[i], replacements[++i]);
        }
        return msg;
    }


    /**
     * Reloads every reloadable class, reloading the plugin.
     *
     * @param deep True to re define the value of {@link #messagesYamlFile}. Usually not necessary.
     * @see Reloadable
     */
    @Override
    public void reload(boolean deep) {
        if (deep) this.messagesYamlFile = new YamlFile(this.plugin, this.messagesYamlFile.getFileName());
        loadMessages();
    }


}
