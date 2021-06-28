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

import com.github.alfonsoleandro.mputils.guis.utils.GUIType;
import org.bukkit.inventory.ItemStack;

/**
 * An unordered GUI that is a {@link SimpleGUI} if the amount of items are lesser than 54, or
 * a {@link PaginatedGUI} in any other case.
 */
public class DynamicGUI extends GUI{

    protected DynamicGUI(String title, int size, String guiTags, GUIType guiType) {
        super(title, size, guiTags, guiType);
    }

    @Override
    public void clearInventory() {
        //TODO
    }

    @Override
    public void addItem(ItemStack item) {
        //TODO
    }
}
