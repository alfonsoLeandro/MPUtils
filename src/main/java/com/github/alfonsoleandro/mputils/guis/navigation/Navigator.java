package com.github.alfonsoleandro.mputils.guis.navigation;

import org.bukkit.inventory.Inventory;

/**
 * Represents a series of items to be shown in a {@link com.github.alfonsoleandro.mputils.guis.Navigable} GUI.
 *
 * @author alfonsoLeandro
 * @since 1.10.0
 */
public abstract class Navigator {

    /**
     * The array of buttons inside this navigationBar.
     */
    protected GUIButton[] buttons;

    /**
     * Sets the default buttons for this Navigation Bar.
     */
    public abstract void setDefaultButtons();

    /**
     * Gets the button in the given position.
     *
     * @param i The position of the button relative to the navigation bar.
     * @return The button in the given position.
     */
    public GUIButton getButtonAt(int i){
        return this.buttons[i];
    }

    /**
     * Sets the button in the given position.
     *
     * @param i      The position of the button relative to the navigation bar (0-8).
     * @param button The new button.
     */
    public void setButtonAt(int i, GUIButton button) {
        this.buttons[i] = button;
    }

    /**
     * Gets all the buttons in this Navigator.
     * @return All the buttons belonging to this Navigator.
     */
    public GUIButton[] getButtons() {
        return this.buttons;
    }

    /**
     * Adds the navigation bar to a {@link com.github.alfonsoleandro.mputils.guis.Navigable}.
     *
     * @param inv        The GUI inventory.
     * @param page       The page the GUI is going to open in.
     * @param totalPages The total amount of pages of the PaginatedGUI.
     */
    public abstract void addNavigationBar(Inventory inv, int page, int totalPages);
}
