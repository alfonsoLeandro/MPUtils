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
package com.github.alfonsoleandro.mputils.reloadable;

/**
 * Represents a class that can and should be reloaded when the plugin reloads.
 */
public abstract class Reloadable {

    /**
     * Creates a new Reloadable and registers it in the given {@link ReloaderPlugin}.
     *
     * @param plugin The {@link ReloaderPlugin} to register this Reloadable in.
     */
    public Reloadable(ReloaderPlugin plugin) {
        plugin.registerReloadable(this);
    }

    /**
     * Reloads this reloadable class.
     *
     * @deprecated Use {@link #reload(boolean)} instead.
     */
    @Deprecated
    public void reload() {
    }

    /**
     * Reloads this reloadable class.
     *
     * @param deep Whether the reload will be deep
     *             (Some actions may impact performance more than others, and they are not to be reloaded every time)
     */
    public abstract void reload(boolean deep);


}
