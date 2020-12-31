package com.cupshe.ak.request;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.core.Kv;
import com.cupshe.ak.core.Kvs;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * RequestHeaderUtils
 *
 * @author zxy
 */
public class RequestHeaderUtils {

    public static String getRequestHeader(String key) {
        Map<String, String> store = BaseConstant.REQ_HEADERS_STORE.get();
        return store != null ? store.get(key) : null;
    }

    public static Kvs getRequestHeaders() {
        Kvs result = new Kvs();
        Map<String, String> store = BaseConstant.REQ_HEADERS_STORE.get();
        Optional.ofNullable(store)
                .orElse(Collections.emptyMap())
                .forEach((key, value) -> result.add(new Kv(key, value)));
        return result;
    }

    public static Map<String, String> getRequestHeaders(HttpServletRequest req) {
        Map<String, String> storeHeaders = new LinkedHashMap<>();
        Enumeration<String> headerNames = req.getHeaderNames();
        for (String key; headerNames.hasMoreElements(); ) {
            key = headerNames.nextElement();
            storeHeaders.put(key, req.getHeader(key));
        }

        return storeHeaders;
    }
}
