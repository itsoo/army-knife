package com.cupshe.ak.objects;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * ArrayUtils
 *
 * @author zxy
 */
public class ArrayUtils {

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isEmpty(T[][] array) {
        return array == null || array.length == 0;
    }

    @SafeVarargs
    public static <T> T[] concat(T[] array, T... elements) {
        if (array == null || isEmpty(elements)) {
            return array;
        }

        T[] result = newArray(array, elements);
        int start = concatAndGetLength(array, result, 0);
        concatAndGetLength(elements, result, start);
        return result;
    }

    @Deprecated
    @SafeVarargs
    public static <T> T[] concat(T[] array, T[]... arrays) {
        return merge(array, arrays);
    }

    @SafeVarargs
    public static <T> T[] merge(T[] array, T[]... arrays) {
        if (isEmpty(arrays)) {
            return array;
        }

        T[] result = newArray(array, arrays);
        int start = 0;
        if (array != null) {
            start = concatAndGetLength(array, result, 0);
        }
        // other arrays
        for (T[] arr : arrays) {
            if (arr != null) {
                start += concatAndGetLength(arr, result, start);
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(T[] array, T[]... elements) {
        return (T[]) Array.newInstance(
                getArrayType(array, elements),
                getTotalLength(array, elements));
    }

    @SafeVarargs
    public static <T> int getTotalLength(T[] array, T[]... arrays) {
        int length = array == null ? 0 : array.length;
        return length + Arrays.stream(arrays)
                .parallel()
                .filter(Objects::nonNull)
                .mapToInt(t -> t.length)
                .sum();
    }

    @SafeVarargs
    public static <T> Class<?> getArrayType(T[] array, T[]... arrays) {
        Class<?> result = getArrayType(array);
        if (result == null) {
            if (arrays != null) {
                for (T[] arr : arrays) {
                    if (arr != null) {
                        result = getArrayType(arrays[0]);
                        break;
                    }
                }
            }
        }

        Objects.requireNonNull(result, "cannot found array type.");
        return result;
    }

    public static <T> Class<?> getArrayType(T[] array) {
        return array != null
                ? array.getClass().getComponentType()
                : null;
    }

    public static <T> int concatAndGetLength(T[] src, T[] dest, int start) {
        System.arraycopy(src, 0, dest, start, src.length);
        return src.length;
    }
}
