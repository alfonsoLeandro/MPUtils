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
package com.github.alfonsoleandro.mputils.itemstacks;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Class containing several utility methods related to inventories.
 * @since 1.10.0
 */
public class InventoryUtils {

    private InventoryUtils() throws IllegalAccessException {
        throw new IllegalAccessException("This class cannot be instantiated!");
    }

    /**
     * Checks if an item can be added to a given Inventory.
     * @param item The item to add.
     * @param inv The inventory where the item is trying to be added to.
     * @return true if the item can be added to the given inventory.
     * @since 1.10.0
     */
    public boolean canAdd(ItemStack item, Inventory inv){
        if(inv.firstEmpty() != -1) return true;

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack inSlot = inv.getItem(i);
            assert inSlot != null;
            if(inSlot.isSimilar(item) && inSlot.getAmount()+item.getAmount() <= item.getMaxStackSize()){
                return true;
            }
        }
        return false;
    }


}
