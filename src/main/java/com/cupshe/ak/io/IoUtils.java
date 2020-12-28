package com.cupshe.ak.io;

import com.cupshe.ak.text.StringUtils;
import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * IoUtils
 *
 * @author zxy
 */
public class IoUtils {

    public static OutputStreamWriter getWriter(OutputStream out, Charset charset) {
        if (null == out) {
            return null;
        }

        if (null == charset) {
            return new OutputStreamWriter(out);
        } else {
            return new OutputStreamWriter(out, charset);
        }
    }

    public static void write(OutputStream out, Object... contents) {
        write(out, StandardCharsets.UTF_8, contents);
    }

    @SneakyThrows
    public static void write(OutputStream out, Charset charset, Object... contents) {
        OutputStreamWriter osw = null;
        try {
            osw = getWriter(out, charset);
            for (Object content : contents) {
                if (content != null) {
                    osw.write(StringUtils.getOrEmpty(content));
                }
            }
            osw.flush();
        } finally {
            close(osw);
        }
    }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception ignore) {}
        }
    }
}
