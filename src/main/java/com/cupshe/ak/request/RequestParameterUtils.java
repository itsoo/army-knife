package com.cupshe.ak.request;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.core.Kvs;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

/**
 * RequestParameterUtils
 *
 * @author zxy
 */
public class RequestParameterUtils {

    public static String[] getParam(String key) {
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            HttpServletRequest req = ((ServletRequestAttributes) rqs).getRequest();
            return req.getParameterValues(key);
        }

        return null;
    }

    public static Kvs getParams() {
        Kvs result = new Kvs();
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            HttpServletRequest req = ((ServletRequestAttributes) rqs).getRequest();
            Optional.ofNullable(req.getParameterMap())
                    .orElse(Collections.emptyMap())
                    .forEach((key, value) -> result.add(new Kv(key, value)));
        }

        return result;
    }
}
