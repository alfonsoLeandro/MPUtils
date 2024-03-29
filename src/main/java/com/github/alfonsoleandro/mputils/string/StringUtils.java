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
package com.github.alfonsoleandro.mputils.string;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class loaded with useful string tools.
 *
 * @author alfonsoLeandro
 * @since 0.9.0
 */
public class StringUtils {


    /**
     * Colorizes a string using {@link ChatColor} RGB hex color codes AND {@link ChatColor#translateAlternateColorCodes(char, String)}
     * or in case the server version is older than 1.16.1 only uses {@link ChatColor#translateAlternateColorCodes(char, String)}.
     *
     * @param alternateColorCode The alternate color code to check for in replacement of '§'.
     * @param string             The string to give color to.
     * @return An empty string if the given string is null and a message to console or the colored string.
     */
    public static String colorizeString(char alternateColorCode, String string) {
        if (string == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[MPUtils] There was an error while colorizing a string, check your config");
            return "";
        }
        Pattern pattern = Pattern.compile("&#[0-9a-fA-F]{6}");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            try {
                string = string.replace(matcher.group(), net.md_5.bungee.api.ChatColor.of(matcher.group().replace("&", "")).toString());
            } catch (Error | Exception e) {
                //Version < 1.16.1 = No RGB
                break;
            }
        }

        return ChatColor.translateAlternateColorCodes(alternateColorCode, string);
    }


    /**
     * Calls {@link #colorizeString(char, String)} using {@literal '&'} as the alternate color code.
     * Supports {@literal &} and any number from 0 through 9, or any letter with 'a' through 'f' or
     * any RGB hex color code of 6 digits using the format {@literal '&#RRGGBB'}.
     *
     * @param string The string to apply color to.
     * @return The given string, but with color codes replaced with the color they represent.
     */
    public static String colorizeString(String string) {
        return colorizeString('&', string);
    }


    /**
     * Checks if a string is either null or empty ("").
     *
     * @param string The string to check.
     * @return true if the string is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    /**
     * Counts the amount of time a string is present in another string.
     *
     * @param string    The string where to count matches.
     * @param subString The string that should be found in the first string to count one more match.
     * @return 0 if any of the parameter Strings is null or empty, in other case,
     * the amount of time the second string is found in the first string.
     */
    public static int countMatches(String string, String subString) {
        if (isNullOrEmpty(string)) return 0;
        if (isNullOrEmpty(subString)) return 0;

        Matcher matcher = Pattern.compile(subString).matcher(string);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * Replaces all the placeholders in a string with the values in the map.
     *
     * @param original     The original string.
     * @param placeholders The map with the placeholders and their values.
     * @return The string with all the placeholders replaced.
     */
    public static String replacePlaceholders(String original, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            original = original.replace(entry.getKey(), entry.getValue());
        }
        return original;
    }
}
