package com.github.alfonsoleandro.mputils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages tab completion for commands, using a list of command arguments.
 * <br>
 * Placeholders available:
 * <ul>
 *     <li>{PLAYERS} - Will return the list of all players online.</li>
 *     <li>{WORLDS} - Will return the list of all worlds available.</li>
 * </ul>
 *
 * @author alfonsoLeandro
 * @since 1.10.0
 */
public class MPTabCompleter implements TabCompleter {

    private final List<String[]> commands = new ArrayList<>();
    private final String requiredPermission;

    /**
     * Sets the commands to be tab completed, these commands should only contain their arguments, for example,
     * if a command is "/plugin do stuff", the argument should be "do stuff".
     * No permission is required to see the plugin's command autocompletion.
     *
     * @param argsPerCommand The args of each command available to be autocompleted.
     */
    public MPTabCompleter(List<String> argsPerCommand) {
        this(argsPerCommand, null);
    }

    /**
     * Sets the commands to be tab completed, these commands should only contain their arguments, for example,
     * if a command is "/plugin do stuff", the argument should be "do stuff".
     *
     * @param argsPerCommand The args of each command available to be autocompleted.
     * @param requiredPermission The required permission to see all of your plugin's command autocompletion.
     */
    public MPTabCompleter(List<String> argsPerCommand, String requiredPermission) {
        for (String command : argsPerCommand) {
            this.commands.add(command.split(" "));
        }
        this.requiredPermission = requiredPermission;
    }

    /**
     * Returns a list of command suggestions based on the args written by the player.
     * In the case of "{PLAYERS}" given as a suggestion, it will return the list of all players online.
     * In the cas of "{WORLDS}" given as a suggestion, it will return the list of all worlds available.
     *
     * @param sender  The sender of the command.
     * @param command The command being tab completed.
     * @param label   The label of the command.
     * @param args    The arguments written by the player.
     * @return A list of suggestions for completing a given command's arguments.
     */
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (this.requiredPermission != null && sender instanceof Player && !sender.hasPermission(this.requiredPermission)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        int inputAmount = args.length;
        String input = args[inputAmount - 1];
        int inputArgLength = input.length();

        // The command is not written at all
        if (input.isEmpty()) {
            outer:
            for (String[] strings : this.commands) {
                if (strings.length < inputAmount) {
                    continue;
                }
                String currentSuggestion = strings[inputAmount - 1];
                if (inputAmount > 1) {
                    for (int i = 0; i < (inputAmount - 1); i++) {
                        if (!strings[i].equalsIgnoreCase(args[i])) {
                            continue outer;
                        }
                    }
                }
                if (currentSuggestion.equalsIgnoreCase("{PLAYERS}")) {
                    list.addAll(Bukkit.getOnlinePlayers().stream().map(CommandSender::getName).toList());
                } else if (currentSuggestion.equalsIgnoreCase("{WORLDS}")) {
                    list.addAll(Bukkit.getWorlds().stream().map(WorldInfo::getName).toList());
                } else {
                    list.add(currentSuggestion);
                }

            }

        // The command is partially written
        } else {
            for (String[] strings : this.commands) {
                if (strings.length < inputAmount) {
                    continue;
                }

                String currentSuggestion = strings[inputAmount - 1];
                if (currentSuggestion.equalsIgnoreCase("{PLAYERS}")) {
                    for (String playerName : Bukkit.getOnlinePlayers().stream().map(CommandSender::getName).toList()) {
                        if (playerName.regionMatches(true, 0, input, 0, inputArgLength)) {
                            list.add(playerName);
                        }
                    }

                } else if (currentSuggestion.regionMatches(true, 0, input, 0, inputArgLength)) {
                    list.add(currentSuggestion);
                }
            }
        }

        return list;
    }
}
