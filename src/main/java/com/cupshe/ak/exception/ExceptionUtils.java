package com.cupshe.ak.exception;

import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ExceptionUtils
 *
 * @author zxy
 */
public class ExceptionUtils {

    @SneakyThrows
    public static String getStackTrace(Throwable throwable) {
        try (StringWriter sw = new StringWriter()) {
            try (PrintWriter pw = new PrintWriter(sw, true)) {
                throwable.printStackTrace(pw);
                return sw.getBuffer().toString();
            }
        }
    }

    public static <R> R rethrow(Throwable throwable) {
        return ExceptionUtils.typeErasure(throwable);
    }

    @SuppressWarnings("unchecked")
    private static <R, T extends Throwable> R typeErasure(Throwable throwable) throws T {
        throw (T) throwable;
    }
}
