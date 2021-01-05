package com.cupshe.ak.io;

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
        if (out == null) {
            return null;
        }

        if (charset == null) {
            return new OutputStreamWriter(out);
        }

        return new OutputStreamWriter(out, charset);
    }

    public static void write(OutputStream out, Object... contents) {
        write(out, StandardCharsets.UTF_8, contents);
    }

    @SneakyThrows
    public static void write(OutputStream out, Charset charset, Object... contents) {
        try (OutputStreamWriter osw = getWriter(out, charset)) {
            for (Object content : contents) {
                if (content != null) {
                    osw.write(content.toString());
                }
            }

            osw.flush();
        }
    }

    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Exception ignore) {}
    }
}
