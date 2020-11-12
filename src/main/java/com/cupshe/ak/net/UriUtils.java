package com.cupshe.ak.net;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.text.StringUtils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UriUtils
 *
 * @author zxy
 */
public class UriUtils {

    private static final Pattern PATTERN = Pattern.compile("(\\{[^}]*})");

    public static String processRequestParamOf(String url, List<Kv> args) {
        if (url == null || args == null) {
            return null;
        }

        if (args.isEmpty()) {
            return url;
        }

        char sp = getQuerySeparator(url);
        StringBuilder result = new StringBuilder(url);
        for (int i = 0, size = args.size(); i < size; i++) {
            if (i > 0) {
                sp = '&';
            }

            String key = args.get(i).getKey();
            if (StringUtils.isNotBlank(key)) {
                result.append(sp).append(key).append('=').append(encode(args.get(i).getValue()));
            }
        }

        return result.toString();
    }

    public static String processPathVariableOf(String url, List<Kv> args) {
        if (url == null || args == null) {
            return null;
        }

        String result = url;
        Iterator<Kv> it = args.iterator();
        for (Kv kv; it.hasNext(); ) {
            kv = it.next();
            String key = kv.getKey();
            Object value = kv.getValue();
            if (StringUtils.isNotEmpty(key) && value != null) {
                result = StringUtils.replace(result, '{' + key + '}', value.toString());
                it.remove();
            }
        }

        Matcher m = PATTERN.matcher(result);
        if (m.find()) {
            int size = Math.min(m.groupCount(), args.size());
            for (int i = 0; i < size; i++) {
                Object value = args.get(i).getValue();
                if (value != null) {
                    result = StringUtils.replace(result, m.group(i + 1), value.toString());
                }
            }
        }

        return result;
    }

    public static String processStandardUri(String prefix, String path) {
        String result = '/' + prefix + '/' + path;
        for (String repeatSp = "//"; result.contains(repeatSp); ) {
            result = StringUtils.replace(result, repeatSp, "/");
        }

        result = "/".equals(result) ? "" : result;
        return result.endsWith("/") ? result.substring(0, result.length() - 1) : result;
    }

    public static String processParamsOfUri(String uri, String[] params) {
        // query params
        String[] qps = params.clone();
        for (int i = 0; i < qps.length; i++) {
            String[] kv = StringUtils.split(qps[i], "=");
            if (kv != null) {
                qps[i] = kv[0] + '=' + encode(kv[1]);
            }
        }

        String query = qps.length == 0 ? "" : getQuerySeparator(uri) + String.join("&", qps);
        return uri + query;
    }

    public static char getQuerySeparator(String uri) {
        return uri.lastIndexOf('?') > -1 ? '&' : '?';
    }

    public static String encode(Object value) {
        return StringUtils.isEmpty(value) ? "" : encode(value.toString(), StandardCharsets.UTF_8);
    }

    public static String encode(String source, Charset charset) {
        return encodeUriComponent(source, charset);
    }

    private static String encodeUriComponent(String source, Charset charset) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }

        byte[] bytes = source.getBytes(charset);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        boolean changed = false;
        for (byte b : bytes) {
            if (b < 0) {
                b += 256;
            }

            if (isAllowed(b)) {
                bos.write(b);
            } else {
                bos.write('%');
                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
                bos.write(hex1);
                bos.write(hex2);
                changed = true;
            }
        }

        return (changed ? new String(bos.toByteArray(), charset) : source);
    }

    private static boolean isAllowed(int c) {
        return (isAlpha(c) || isDigit(c) || '-' == c || '.' == c || '_' == c || '~' == c);
    }

    private static boolean isAlpha(int c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(int c) {
        return (c >= '0' && c <= '9');
    }
}
