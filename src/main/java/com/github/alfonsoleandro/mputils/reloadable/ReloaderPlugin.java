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
package com.github.alfonsoleandro.mputils.reloadable;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a {@link JavaPlugin} that can be reloaded and when it does,
 * it reloads every {@link Reloadable} registered in this class.
 */
public abstract class ReloaderPlugin extends JavaPlugin {

    /**
     * The collection of {@link Reloadable} classes to reload when the plugin reloads.
     * @see #reload()
     */
    protected final Set<Reloadable> reloadables = new HashSet<>();

    /**
     * Adds a new Reloadable to the reloadables collection.
     * @param reloadable The {@link Reloadable} to add.
     */
    public void registerReloadable(Reloadable reloadable){
        this.reloadables.add(reloadable);
    }

    /**
     * Removes a Reloadable from the reloadables collection.
     * @param reloadable The {@link Reloadable} to remove.
     */
    public void unRegisterReloadable(Reloadable reloadable){
        this.reloadables.remove(reloadable);
    }

    /**
     * Reloads every reloadable class, reloading the plugin.
     * @see Reloadable
     * @deprecated Use {@link #reload(boolean)} instead.
     */
    @Deprecated
    public void reload(){
        this.reloadables.forEach(r -> r.reload(false));
    }
    /**
     * Reloads every reloadable class, reloading the plugin.
     * @param deep Whether or not this reload will be deep
     *             (Some actions may have a bigger performance impact than others and they are not to be reloaded every time)
     * @see Reloadable
     */
    public void reload(boolean deep){
        this.reloadables.forEach(r -> r.reload(deep));
    }
}
