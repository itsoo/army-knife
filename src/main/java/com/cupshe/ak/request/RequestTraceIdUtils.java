package com.cupshe.ak.request;

import com.cupshe.ak.net.UuidUtils;
import com.cupshe.ak.text.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.cupshe.ak.common.BaseConstant.TRACE_ID_KEY;

/**
 * RequestTraceIdUtils
 *
 * @author zxy
 */
public class RequestTraceIdUtils {

    public static String genericTraceId() {
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            return getTraceId(((ServletRequestAttributes) rqs).getRequest());
        }

        return StringUtils.defaultIfBlank(getTraceId(), UuidUtils.createUuid());
    }

    public static String getTraceId() {
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            HttpServletRequest req = ((ServletRequestAttributes) rqs).getRequest();
            return req.getHeader(TRACE_ID_KEY);
        }

        return null;
    }

    public static String getTraceId(HttpServletRequest req) {
        return StringUtils.defaultIfBlank(req.getHeader(TRACE_ID_KEY), getTraceId());
    }
}
