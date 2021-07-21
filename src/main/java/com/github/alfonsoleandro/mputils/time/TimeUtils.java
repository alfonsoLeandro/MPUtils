/*
Copyright (c) 2020 Leandro Alfonso

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
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.github.alfonsoleandro.mputils.time;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing various useful time related methods.
 *
 * @author lelesape
 */
public class TimeUtils {

    /**
     * Private constructor so this class cannot be instantiated
     */
    private TimeUtils(){
        throw new IllegalStateException("TimeUtils is only a utility class!");
    }



    /**
     * Gets the amount of ticks a given amount of time of the given unit represents.
     * @param amount The amount of time of the given time unit.
     * @param timeUnit The timeunit for the given amount.
     * @return The value in ticks of the given time amount.
     */
    public static int getTicks(int amount, TimeUnit timeUnit){
        return amount * timeUnit.getMultiplier();
    }

    /**
     * Gets the amount of ticks a given string represents.
     * @param timeString The string representing an amount of time. Must be in a "XT" format, where X
     *                   is any positive integer and T is a time format.
     * @return The value in ticks of the given time amount.
     * @see TimeUnit
     */
    public static int getTicks(String timeString){
        if(timeString == null || timeString.isEmpty() || timeString.length() < 2) return 0;
        return getTicks(Integer.parseInt(timeString.substring(0, timeString.length()-1)),
                TimeUnit.getByAlias(timeString.charAt(timeString.length()-1)));
    }

    /**
     * Gets the amount of ticks a given amount of time of the given unit represents.
     * @param amount The amount of time of the given time unit.
     * @param timeUnit The char representing the timeunit for the given amount.
     * @return The value in ticks of the given time amount.
     */
    public static int getTicks(int amount, char timeUnit){
        return getTicks(amount, TimeUnit.getByAlias(timeUnit));
    }

    /**
     * Translates and amount of ticks into weeks, days, hours, minutes and seconds.
     * From this string you will need to replace %weeks%, %week%, %days%, %day%, %hours%, %hour%, %minutes%,
     * %minute%, %seconds%, %second% and %and% placeholders.
     * @param ticks The amount of ticks to translate
     * @return A string with an w,d,h,m and s format.
     */
    public static String getTimeString(long ticks){
        List<String> args = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        final long weeks = getWeeks(ticks);
        final long days = getDays(ticks);
        final long hours = getHours(ticks);
        final long minutes = getMinutes(ticks);
        final long seconds = getSeconds(ticks);


        if(weeks > 0){
            String s = weeks +
                    (weeks > 1 ? "%weeks%" : "%week%");

            args.add(s);
        }

        if(days > 0){
            String s = days +
                    (days > 1 ? "%days%" : "%day%");

            args.add(s);
        }

        if(hours > 0){
            String s = hours +
                    (hours > 1 ? "%hours%" : "%hour%");

            args.add(s);
        }

        if(minutes > 0){
            String s = minutes +
                    (minutes > 1 ? "%minutes%" : "%minute%");
            args.add(s);
        }

        String s = (seconds >= 0 ? seconds : 0) +
                (seconds == 0 || seconds > 1 ? "%seconds%" : "%second%");
        args.add(s);


        for (int i = 0; i < args.size(); i++) {

            if(args.size() > 1 && i != 0) {
                sb.append(i == args.size()-1 ? " "+"%and%"+" " : ", ");
            }

            sb.append(args.get(i));
        }

        return sb.toString();
    }

    /**
     * Gets the total amount of seconds a given amount of ticks represents.
     * @param ticks The ticks to translate to seconds.
     * @return The amount of seconds the given amount of ticks represent.
     */
    public static long getTotalSeconds(long ticks){
        return ticks/20;
    }

    /**
     * Gets only the amount of seconds (between 0 and 59) an amount of ticks represent.
     * @param ticks The amount of ticks.
     * @return A number between 0 and 59 representing the seconds for the given amount of ticks.
     */
    public static long getSeconds(long ticks){
        return java.util.concurrent.TimeUnit.SECONDS.toSeconds(getTotalSeconds(ticks)) - java.util.concurrent.TimeUnit.MINUTES.toSeconds(getMinutes(ticks)) - java.util.concurrent.TimeUnit.HOURS.toSeconds(getHours(ticks)) - java.util.concurrent.TimeUnit.DAYS.toSeconds(getDays(ticks)) - java.util.concurrent.TimeUnit.DAYS.toSeconds(getWeeks(ticks)*7);
    }

    /**
     * Gets only the amount of minutes (between 0 and 59) an amount of ticks represent.
     * @param ticks The amount of ticks.
     * @return A number between 0 and 59 representing the minutes for the given amount of ticks.
     */
    public static long getMinutes(long ticks){
        return java.util.concurrent.TimeUnit.SECONDS.toMinutes(getTotalSeconds(ticks)) - java.util.concurrent.TimeUnit.HOURS.toMinutes(getHours(ticks)) - java.util.concurrent.TimeUnit.DAYS.toMinutes(getDays(ticks)) - java.util.concurrent.TimeUnit.DAYS.toMinutes(getWeeks(ticks)*7);
    }

    /**
     * Gets only the amount of hours (between 0 and 23) an amount of ticks represent.
     * @param ticks The amount of ticks.
     * @return A number between 0 and 23 representing the hours for the given amount of ticks.
     */
    public static long getHours(long ticks){
        return java.util.concurrent.TimeUnit.SECONDS.toHours(getTotalSeconds(ticks)) - java.util.concurrent.TimeUnit.DAYS.toHours(getDays(ticks)) - java.util.concurrent.TimeUnit.DAYS.toHours(getWeeks(ticks)*7);
    }

    /**
     * Gets only the amount of days (between 0 and 6) an amount of ticks represent.
     * @param ticks The amount of ticks.
     * @return A number between 0 and 6 representing the days for the given amount of ticks.
     */
    public static long getDays(long ticks){
        return java.util.concurrent.TimeUnit.SECONDS.toDays(getTotalSeconds(ticks)) - getWeeks(ticks)*7;
    }

    /**
     * Gets only the amount of weeks an amount of ticks represent.
     * @param ticks The amount of ticks.
     * @return A number representing the weeks for the given amount of ticks.
     */
    public static long getWeeks(long ticks){
        return java.util.concurrent.TimeUnit.SECONDS.toDays(getTotalSeconds(ticks))/7;
    }



}
