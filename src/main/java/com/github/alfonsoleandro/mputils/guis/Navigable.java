package com.github.alfonsoleandro.mputils.guis;

import com.github.alfonsoleandro.mputils.guis.navigation.NavigationBar;
import org.bukkit.entity.Player;
import com.github.alfonsoleandro.mputils.guis.utils.GUIType;

/**
 * Represents a GUI that can be navigable (has Navigation bar and pages).
 */
public abstract class Navigable extends GUI{

    /**
     * The navigation bar present in this GUI.
     */
    protected NavigationBar navBar;
    /**
     * The total number of pages this GUI has.
     */
    protected int pages;

    /**
     * Constructor for ANY Navigable GUI with its essential features.
     *
     * @param title   The title for the inventory (colors must be applied before).
     * @param size    The size for the inventory.
     * @param guiTags Any String chosen to distinguish this GUI from another GUI.
     * @param guiType The type of GUI. Either {@link GUIType#SIMPLE} or {@link GUIType#PAGINATED}.
     * @param navBar The {@link NavigationBar} object that goes with this Navigable GUI.
     */
    protected Navigable(String title, int size, String guiTags, GUIType guiType, NavigationBar navBar) {
        super(title, size, guiTags, guiType);
        this.navBar = navBar;
    }

    /**
     * Gets the navigation bar object that this GUI is currently using.
     * @return The NavigationBar object being used.
     */
    public NavigationBar getNavBar(){
        return this.navBar;
    }

    /**
     * Sets the navigation bar that this GUI will be using.
     * @param navBar The navigation bar you want this GUI to use.
     * @see NavigationBar
     */
    public void setNavBar(NavigationBar navBar){
        this.navBar = navBar;
    }

    /**
     * Changes the items inside the inventory for the items in the given page.
     * @param player The player to set the GUI page for.
     * @param page The page to set the items for.
     */
    public abstract void setPage(Player player, int page);

    /**
     * Opens the GUI in the given page for a given player.
     * @param player The player to open the GUI for.
     * @param page The page to open the GUI in.
     * @since 1.10.0
     */
    public abstract void openGUI(Player player, int page);

    /**
     * Updates the navigation bar, called when the GUI is opened for a user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     */
    public void setNavBarForPage(int page){
        this.navBar.addNavigationBar(this.inventory, page, getPages());
    }

    /**
     * Gets the amount of pages on this NavigableGUI.
     * @return -1 if this GUI is not paginated, the amount of pages in any other case.
     */
    public int getPages(){
        return this.pages;
    }
}
