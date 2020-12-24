package com.cupshe.ak.request;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.core.Kv;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<Kv> getRequestHeaders() {
        Map<String, String> store = BaseConstant.REQ_HEADERS_STORE.get();
        return store.entrySet()
                .parallelStream()
                .map(t -> new Kv(t.getKey(), t.getValue()))
                .collect(Collectors.toList());
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
