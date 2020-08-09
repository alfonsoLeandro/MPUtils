package com.github.alfonsoLeandro.MPUtils.String;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class loaded with useful string tools
 */
public class StringUtils {


    /**
     * Colorizes a string using {@link ChatColor} RGB hex color codes AND {@link ChatColor#translateAlternateColorCodes(char, String)}
     * or in case the server version is older than 1.16.1 only uses {@link ChatColor#translateAlternateColorCodes(char, String)}.
     *
     * @param alternateColorCode The alternate color code to check for in replacement of '§'.
     * @param string The string to give color to.
     * @return An empty string if the given string is null and a message to console or the colored string.
     */
    public static String colorizeString(char alternateColorCode, String string){
        if(string == null){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"[MPUtils] There was an error while colorizing a string, check your config");
            return "";
        }
        final char[] chars = string.toCharArray();
        final List<String> hexColors = new ArrayList<>();

        //Check every character on the string looking for the "(colorCode)#" combination
        //length-1 in order to ensure there are 2 characters to look for
        outer: for (int i = 0; i < chars.length-1; i++) {
            if(chars[i] == alternateColorCode && chars[i+1] == '#'){
                final StringBuilder builder = new StringBuilder();

                //find the color combination #RRGGBB
                for (int j = i+1; j < i+8; j++) {
                    if(j < chars.length) {
                        builder.append(chars[j]);
                    }else{
                        break outer;
                    }
                }
                //Try adding the hex colors, if version < 1.16.1 it will still apply the usual colors
                // (translateAlternateColorCodes) and return
                try{
                    net.md_5.bungee.api.ChatColor.of(builder.toString());
                    hexColors.add(builder.toString());
                }catch (Error | Exception e){
                    if(e instanceof NoSuchMethodError){
                        return ChatColor.translateAlternateColorCodes(alternateColorCode, string);
                    }
                }
            }

        }
        for(String hex : hexColors){
            string = string.replace(alternateColorCode+hex, String.valueOf(net.md_5.bungee.api.ChatColor.of(hex)));
        }

        return ChatColor.translateAlternateColorCodes(alternateColorCode, string);
    }
}
