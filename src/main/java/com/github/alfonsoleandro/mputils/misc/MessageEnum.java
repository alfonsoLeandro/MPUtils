package com.github.alfonsoleandro.mputils.misc;

import org.jetbrains.annotations.NotNull;

/**
 * Simple interface containing methods that an enum containing a list of messages for
 * the {@link com.github.alfonsoleandro.mputils.managers.MessageSender} should have.
 */
public interface MessageEnum{


    /**
     * Gets the path where a message should be found inside a given configuration file.
     * @return The path where to find a message.
     */
    @NotNull String getPath();


    /**
     * Gets the default value for a message in case the value from the file returns null.
     * @return A default value for this message in case the configurable value returns null.
     */
    @NotNull String getDefault();

}
