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
        return obj == null || isEquals(EMPTY, obj.toString());
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int length;
        if (cs == null || (length = cs.length()) == 0) {
            return true;
        }

        for (int i = 0; i < length; i++) {
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
        String ts = trim(str);
        return isEmpty(ts) ? EMPTY : ts;
    }

    public static String getOrDefault(Object obj, Object defaultObj) {
        return obj == null
                ? defaultObj == null ? null : defaultObj.toString()
                : obj.toString();
    }

    public static String getOrEmpty(Object obj) {
        return getOrDefault(obj.toString(), EMPTY);
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

        int i = str.indexOf(oldPattern);
        if (i == -1) {
            return str;
        }

        int capacity = str.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }

        StringBuilder result = new StringBuilder(capacity);
        int j = 0, length = oldPattern.length();
        while (i >= 0) {
            result.append(str, j, i);
            result.append(newPattern);
            j = i + length;
            i = str.indexOf(oldPattern, j);
        }

        result.append(str.substring(j));
        return result.toString();
    }

    public static String[] split(String str, String delimiter) {
        if (isEmpty(str) || isEmpty(delimiter)) {
            return null;
        }

        int i = str.indexOf(delimiter);
        if (i < 0) {
            return null;
        }

        return new String[]{
                str.substring(0, i),
                str.substring(i + delimiter.length())
        };
    }

    public static String upperFirstLetter(String str) {
        if (isBlank(str)) {
            return str;
        }

        char c = str.charAt(0);
        if (needUpperFirstLetter(c)) {
            c -= 32;
        }

        return c + (str.length() > 1 ? str.substring(1) : EMPTY);
    }

    public static String lowerFirstLetter(String str) {
        if (isBlank(str)) {
            return str;
        }

        char c = str.charAt(0);
        if (needLowerFirstLetter(c)) {
            c += 32;
        }

        return c + (str.length() > 1 ? str.substring(1) : EMPTY);
    }

    public static String trimLeadingCharacter(String str, char c) {
        if (str == null) {
            return null;
        }

        int i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) != c) {
                break;
            }
        }

        return str.substring(i);
    }

    public static String trimTrailingCharacter(String str, char c) {
        if (str == null) {
            return null;
        }

        int i = str.length() - 1;
        for (; i >= 0; i--) {
            if (str.charAt(i) != c) {
                break;
            }
        }

        return str.substring(0, i + 1);
    }

    public static int findSubstringCountOf(String str, String subStr) {
        if (isBlank(str) || isBlank(subStr)) {
            return 0;
        }

        int count = 0, j = 0, i;
        while ((i = str.indexOf(subStr, j)) != -1) {
            ++count;
            j = i + subStr.length();
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
