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
package com.github.alfonsoleandro.mputils.guis;

/**
 * Class containing various GUI's details for using on MPUtils' custom events.
 */
public class GUIAttributes {

    /**
     * The page number the player is currently on.
     */
    final private int page;
    /**
     * The type of GUI the player has open.
     */
    final private GUIType guiType;
    /**
     * The actual GUI object.
     */
    final private GUI gui;

    public GUIAttributes(int page, GUIType guiType, GUI gui){
        this.page = page;
        this.guiType = guiType;
        this.gui = gui;
    }

    /**
     * Gets the page number the player was last seen on.
     * @return The GUI page number or -1 if the {@link GUIType} is equal to {@link SimpleGUI}.
     */
    public int getPage() {
        return page;
    }

    /**
     * Gets the GUI type.
     * @return Either {@link SimpleGUI} or {@link PaginatedGUI}.
     */
    public GUIType getGuiType() {
        return guiType;
    }

    /**
     * Gets the GUI object.
     * @return The GUI object.
     */
    public GUI getGui(){
        return gui;
    }

}
