package com.cupshe.ak.net;

import java.util.UUID;

/**
 * UuidUtils
 *
 * @author zxy
 */
public class UuidUtils {

    public static String createUuid() {
        return createUuid(UUID.randomUUID().toString());
    }

    public static String createUuid(String uuid) {
        char[] dest = new char[32];
        copyArray(dest, uuid.toCharArray());
        return new String(dest);
    }

    private static void copyArray(char[] dest, char[] src) {
        System.arraycopy(src, 0, dest, 0, 8);
        System.arraycopy(src, 9, dest, 8, 4);
        System.arraycopy(src, 14, dest, 12, 4);
        System.arraycopy(src, 19, dest, 16, 4);
        System.arraycopy(src, 24, dest, 20, 12);
    }
}
