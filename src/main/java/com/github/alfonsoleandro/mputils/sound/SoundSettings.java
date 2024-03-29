/*
Copyright (c) 2023 Leandro Alfonso

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
package com.github.alfonsoleandro.mputils.sound;

import org.bukkit.Sound;

/**
 * Settings for a specific sound.
 *
 * @author alfonsoLeandro.
 * @see SoundUtils
 * @since 1.10.0
 */
public class SoundSettings {

    /**
     * The pitch of the sound.
     */
    private final float pitch;
    /**
     * The sound.
     */
    private final Sound sound;
    /**
     * The volume of the sound.
     */
    private final float volume;

    /**
     * Class constructor.
     *
     * @param sound  The sound.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public SoundSettings(String sound, double volume, double pitch) {
        this(Sound.valueOf(sound), volume, pitch);
    }

    /**
     * Class constructor.
     *
     * @param sound  The sound.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public SoundSettings(Sound sound, double volume, double pitch) {
        this.sound = sound;
        this.volume = (float) volume;
        this.pitch = (float) pitch;
    }


    /**
     * Gets the sound.
     *
     * @return The sound.
     */
    public Sound getSound() {
        return this.sound;
    }

    /**
     * Gets the volume of the sound.
     *
     * @return The volume of the sound.
     */
    public float getVolume() {
        return this.volume;
    }

    /**
     * Gets the pitch of the sound.
     *
     * @return The pitch of the sound.
     */
    public float getPitch() {
        return this.pitch;
    }
}