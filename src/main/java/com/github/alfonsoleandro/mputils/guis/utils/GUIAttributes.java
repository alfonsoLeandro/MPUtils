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
package com.github.alfonsoleandro.mputils.guis.utils;

import com.github.alfonsoleandro.mputils.guis.GUI;
import com.github.alfonsoleandro.mputils.guis.PaginatedGUI;
import com.github.alfonsoleandro.mputils.guis.SimpleGUI;

/**
 * Class containing various GUI's details for using on MPUtils' custom events.
 *
 * @param gui     The actual GUI object.
 * @param guiType The type of GUI the player has open.
 * @param page    The page number the player is currently on.
 * @author alfonsoLeandro
 * @since 0.9.0
 */
public record GUIAttributes(int page, GUIType guiType, GUI gui) {

    /**
     * @param page    The page number the player was last seen on.
     * @param guiType Either {@link SimpleGUI} or {@link PaginatedGUI}.
     * @param gui     The GUI object.
     */
    public GUIAttributes {
    }

    /**
     * Gets the page number the player was last seen on.
     *
     * @return The GUI page number or -1 if the {@link GUIType} is equal to {@link SimpleGUI}.
     */
    @Override
    public int page() {
        return this.page;
    }

    /**
     * Gets the GUI type.
     *
     * @return Either {@link SimpleGUI} or {@link PaginatedGUI}.
     */
    @Override
    public GUIType guiType() {
        return this.guiType;
    }

    /**
     * Gets the GUI object.
     *
     * @return The GUI object.
     */
    @Override
    public GUI gui() {
        return this.gui;
    }

}
