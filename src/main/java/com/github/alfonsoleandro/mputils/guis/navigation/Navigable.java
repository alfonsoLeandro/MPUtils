package com.github.alfonsoleandro.mputils.guis.navigation;

import org.bukkit.entity.Player;

/**
 * Represents a GUI that can be navigable (has Navigation bar and pages).
 */
public interface Navigable {

    /**
     * Gets the navigation bar object that this GUI is currently using.
     * @return The NavigationBar object being used.
     */
    NavigationBar getNavBar();

    /**
     * Sets the navigation bar that this GUI will be using.
     * @param navBar The navigation bar you want this GUI to use.
     * @see NavigationBar
     */
    void setNavBar(NavigationBar navBar);

    /**
     * Changes the items inside the inventory for the items in the given page.
     * @param player The player to set the GUI page for.
     * @param page The page to set the items for.
     */
    void setPage(Player player, int page);

    /**
     * Updates the navigation bar, called when the GUI is opened for an user, replicates the navBar items and
     * replaces the %page%, %nextpage%, %previouspage% and %totalpages% placeholders.
     *
     * @param page The current open page, used for placeholders.
     */
    void setNavBarForPage(int page);

    /**
     * Gets the amount of pages on this DynamicGUI.
     * @return -1 if this GUI is not paginated, the amount of pages in any other case.
     */
    int getPages();




}
