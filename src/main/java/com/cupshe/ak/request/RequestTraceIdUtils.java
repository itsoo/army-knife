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
        String result = null;
        RequestAttributes rqs = RequestContextHolder.getRequestAttributes();
        if (rqs instanceof ServletRequestAttributes) {
            result = getTraceId(((ServletRequestAttributes) rqs).getRequest());
        }

        return StringUtils.defaultIfBlank(result, UuidUtils.createUuid());
    }

    public static String getTraceId(HttpServletRequest req) {
        return req.getHeader(TRACE_ID_KEY);
    }
}
