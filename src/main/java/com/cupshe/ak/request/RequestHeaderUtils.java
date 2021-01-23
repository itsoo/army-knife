package com.cupshe.ak.request;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.core.Kvs;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * RequestHeaderUtils
 *
 * @author zxy
 */
public class RequestHeaderUtils {

    public static String getRequestHeader(String key) {
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            HttpServletRequest req = ((ServletRequestAttributes) rqs).getRequest();
            return req.getHeader(key);
        }

        return null;
    }

    public static Kvs getRequestHeaders() {
        Kvs result = new Kvs();
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            HttpServletRequest req = ((ServletRequestAttributes) rqs).getRequest();
            Enumeration<String> headerNames = req.getHeaderNames();
            for (String key; headerNames.hasMoreElements(); ) {
                key = headerNames.nextElement();
                result.add(new Kv(key, req.getHeader(key)));
            }
        }

        return result;
    }
}
