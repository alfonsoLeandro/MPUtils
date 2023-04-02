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
package com.github.alfonsoleandro.mputils.time;

/**
 * @author alfonsoLeandro
 * @since 1.6.0
 */
public enum TimeUnit {

    /**
     * Ticks, The simplest Time unit for this class.
     */
    TICKS(1),
    /**
     * Seconds, conformed by 20 ticks.
     */
    SECONDS(TICKS.multiplier * 20),
    /**
     * Minutes, conformed by 60 seconds, 1,200 ticks.
     */
    MINUTES(SECONDS.multiplier * 60),
    /**
     * Hours, conformed by 60 minutes, 1,200 seconds, 72,000 ticks.
     */
    HOURS(MINUTES.multiplier * 60),
    /**
     * Days, conformed by 24 hours, 3,600 minutes, 216,000 seconds, 1,728,000 ticks.
     */
    DAYS(HOURS.multiplier * 24),
    /**
     * Weeks, conformed by 7 days 168 hours, 25,200 minutes, 1,512,000 seconds, 12,096,000 ticks.
     */
    WEEKS(DAYS.multiplier * 7);


    /**
     * The value that a value needs to be multiplied to translate to ticks.
     */
    private final int multiplier;


    /**
     * Represents a conventional time unit.
     *
     * @param multiplier The value that a value needs to be multiplied to transform to ticks.
     */
    TimeUnit(int multiplier) {
        this.multiplier = multiplier;
    }


    /**
     * Gets the value that a value needs to be multiplied to transform to ticks.
     *
     * @return The value that a value needs to be multiplied to transform to ticks.
     */
    public int getMultiplier() {
        return this.multiplier;
    }


    /**
     * Gets a timeUnit by its alias (t/T = ticks, m/M = minutes, h/H = hours, d/D = days,
     * w/W = weeks, any other case = seconds).
     *
     * @param alias The alias the timeunit is known by.
     * @return The timeUnit the given char represents.
     */
    public static TimeUnit getByAlias(char alias) {
        switch (alias) {
            case 't':
            case 'T':
                return TICKS;
            case 'm':
            case 'M':
                return MINUTES;
            case 'h':
            case 'H':
                return HOURS;
            case 'd':
            case 'D':
                return DAYS;
            case 'w':
            case 'W':
                return WEEKS;
            default:
                return SECONDS;
        }
    }
}

