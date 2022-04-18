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
package com.github.alfonsoleandro.mputils.guis.navigation;

import com.github.alfonsoleandro.mputils.itemstacks.MPItemStacks;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a clickable button inside the Navigation bar of a PaginatedGUI.
 * @see NavigationBar
 * @see com.github.alfonsoleandro.mputils.guis.PaginatedGUI
 */
public class GUIButton {

    /**
     * A string that identifies this button.
     */
    private final String buttonTags;
    /**
     * The main item of this button.
     */
    private ItemStack item;
    /**
     * The condition this button needs to meet in order to show its main item.
     */
    private GUIButtonCondition condition;
    /**
     * Item shown if the condition is not met.
     */
    private ItemStack backup;

    /**
     * Creates a new GUIButton. These are clickable slots inside the Navigation Bar.
     * @param buttonTags A string that identifies this buttons to distinguish it from another buttons.
     * @param item The item to show if the condition is met.
     * @param condition The condition to meet for the item to show.
     * @param backup The item to show if the condition was not met.
     * @see NavigationBar
     * @see GUIButtonCondition
     */
    public GUIButton(String buttonTags, ItemStack item, GUIButtonCondition condition, ItemStack backup) {
        this.buttonTags = buttonTags;
        this.item = item;
        this.condition = condition;
        this.backup = backup;
    }

    /**
     * Gets the tag associated with this button.
     * @return The identifying string for this button object.
     */
    public String getButtonTags() {
        return this.buttonTags;
    }

    /**
     * Gets the raw (no placeholders replaced) item for this button.
     * @return An ItemStack that represents this button, with no placeholders replaced.
     */
    public ItemStack getRawItem() {
        return this.item;
    }

    /**
     * Gets the raw (no placeholders replaced) backup item for this button (item shown if the condition is not met).
     * @return An ItemStack that represents this button, with no placeholders replaced, when the condition is not met.
     */
    public ItemStack getBackup(){
        return this.backup;
    }

    /**
     * Gets the item to show for this button.
     * If the condition is met, the main item is shown, in other case, the backup item is shown.
     * @param page The number of page this button will belong to.
     * @param totalPages The total number of pages the GUI this button will be shown has.
     * @return Either the item or the backup item.
     */
    public ItemStack getItem(int page, int totalPages) {
        ItemStack item = this.condition.meetsCondition(page ,totalPages) ? this.item : this.backup;
        return MPItemStacks.replacePlaceholders(item.clone(), getPlaceHoldersMap(page, totalPages));
    }

    /**
     * Gets the condition that this button needs to meet in order to show the main item.
     * @return The condition for this button to show its main item.
     */
    public GUIButtonCondition getCondition(){
        return this.condition;
    }

    /**
     * Sets the main item of this button.
     * @param item The new main item.
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }

    /**
     * Sets the condition for this button to show its main item.
     * @param condition The new condition.
     */
    public void setCondition(GUIButtonCondition condition){
        this.condition = condition;
    }

    /**
     * Sets the item to show if the condition is not met.
     * @param backup The new backup item.
     */
    public void setBackupItem(ItemStack backup){
        this.backup = backup;
    }

    /**
     * Gets a Map with every placeholder available for this object with every corresponding value.
     * @param page The page the GUI is in.
     * @return A map containing every placeholder and every value.
     */
    private Map<String, String> getPlaceHoldersMap(int page, int totalPages){
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%page%", String.valueOf(page+1));
        placeholders.put("%nextpage%", String.valueOf(page+2));
        placeholders.put("%previouspage%", String.valueOf(page));
        placeholders.put("%totalpages%", String.valueOf(totalPages));

        return placeholders;
    }

    /**
     * Simple enum containing possible conditions for a button to show its main item.
     */
    public enum GUIButtonCondition {
        /**
         * Will always show its main item. backup can be null.
         */
        ALWAYS,
        /**
         * Will always show its backup item. Main item can be null.
         */
        NEVER,
        /**
         * Will show its main item if there is a next page available. The backup item will be shown otherwise.
         */
        HAS_NEXT_PAGE,
        /**
         * Will show its main item if the current page is not the first page. The backup item will be shown otherwise.
         */
        HAS_PREVIOUS_PAGE;

        /**
         * Checks to see if the given variables meets the condition.
         * @param page The current page the GUi is opened in.
         * @param totalPages The total number of pages this GUI has.
         * @return true if the condition is met.
         */
        public boolean meetsCondition(int page, int totalPages){
            switch (this) {
                case ALWAYS:
                    return true;
                case HAS_NEXT_PAGE:
                    return page+1 < totalPages;
                case HAS_PREVIOUS_PAGE:
                    return page > 0;
            }
            return false;
        }
    }
}
