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
package com.github.alfonsoleandro.mputils.collections;

import com.github.alfonsoleandro.mputils.string.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing several utility methods related to Collections.
 *
 * @author alfonsoLeandro
 * @since 1.10.0
 */
public class CollectionUtils {

    private CollectionUtils() throws IllegalAccessException {
        throw new IllegalAccessException("This class cannot be instantiated!");
    }

    /**
     * Takes a list of unknown type and returns one of the given type, to ensure it only contains that type.
     * @param list The list of unknown type.
     * @param type The class of the type of object to look for.
     * @param <T> The type parameter of the object
     * @return A list containing only the given type of objects.
     * @since 1.10.0
     */
    public static <T> List<T> getListOfType(List<?> list, Class<T> type){
        List<T> result = new ArrayList<>();
        if(list == null) return result;
        for(Object obj : list){
            if(type.isInstance(obj)) {
                result.add(type.cast(obj));
            }
        }
        return result;
    }

    /**
     * Colorizes a String collection.
     * @param collection The collection to colorize.
     * @param <T> Iterable of any type, containing Strings.
     * @return An ArrayList filled with the strings in the first collection, but the strings have colors applied.
     * @since 1.10.0
     */
    public static <T extends Iterable<String>> ArrayList<String> colorizeList(T collection){
        ArrayList<String> result = new ArrayList<>();
        for(String str : collection){
            result.add(StringUtils.colorizeString(str));
        }
        return result;
    }
}
