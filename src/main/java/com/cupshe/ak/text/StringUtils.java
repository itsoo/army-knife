package com.cupshe.ak.text;

import org.slf4j.helpers.MessageFormatter;

/**
 * StringUtils
 *
 * @author zxy
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || isEmpty(obj.toString());
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isEquals(String str, String tar) {
        return str != null && str.equals(tar);
    }

    public static boolean isNotEquals(String str, String tar) {
        return !isEquals(str, tar);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String getOrDefault(Object obj, Object defaultObj) {
        return obj == null ? defaultObj.toString() : obj.toString();
    }

    public static String getOrEmpty(Object obj) {
        return getOrDefault(obj.toString(), EMPTY);
    }

    public static String defaultString(String str) {
        return defaultString(str, EMPTY);
    }

    public static String defaultString(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String replace(String str, String oldPattern, String newPattern) {
        if (isEmpty(str) || isEmpty(oldPattern) || newPattern == null) {
            return str;
        }

        int index = str.indexOf(oldPattern);
        if (index == -1) {
            return str;
        }

        int capacity = str.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }

        StringBuilder result = new StringBuilder(capacity);
        int pos = 0, patLen = oldPattern.length();
        while (index >= 0) {
            result.append(str, pos, index);
            result.append(newPattern);
            pos = index + patLen;
            index = str.indexOf(oldPattern, pos);
        }

        result.append(str.substring(pos));
        return result.toString();
    }

    public static String[] split(String str, String delimiter) {
        if (isEmpty(str) || isEmpty(delimiter)) {
            return null;
        }

        int offset = str.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }

        String beforeDelimiter = str.substring(0, offset);
        String afterDelimiter = str.substring(offset + delimiter.length());
        return new String[]{beforeDelimiter, afterDelimiter};
    }

    public static String upperFirstLetter(String str) {
        char c = str.charAt(0);
        if (needUpperFirstLetter(c)) {
            c -= 32;
        }

        return c + (str.length() > 1 ? str.substring(1) : EMPTY);
    }

    public static String lowerFirstLetter(String str) {
        char c = str.charAt(0);
        if (needLowerFirstLetter(c)) {
            c += 32;
        }

        return c + (str.length() > 1 ? str.substring(1) : EMPTY);
    }

    public static String trimLeadingCharacter(String str, char character) {
        if (isBlank(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(0) == character) {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

    public static String trimTrailingCharacter(String str, char character) {
        if (isBlank(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == character) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static int findSubstringCountOf(String str, String subStr) {
        if (isBlank(str) || isBlank(subStr)) {
            return 0;
        }

        int count = 0, pos = 0, idx;
        while ((idx = str.indexOf(subStr, pos)) != -1) {
            ++count;
            pos = idx + subStr.length();
        }

        return count;
    }

    public static String getFormatString(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

    private static boolean needUpperFirstLetter(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static boolean needLowerFirstLetter(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
