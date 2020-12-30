package com.cupshe.ak.request;

import com.cupshe.ak.net.UuidUtils;
import com.cupshe.ak.text.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static com.cupshe.ak.common.BaseConstant.REQ_TRACE_ID_STORE;
import static com.cupshe.ak.common.BaseConstant.TRACE_ID_KEY;

/**
 * RequestTraceIdUtils
 *
 * @author zxy
 */
public class RequestTraceIdUtils {

    public static String genericTraceId() {
        String result = getTraceId();
        if (StringUtils.isBlank(result)) {
            result = UuidUtils.createUuid();
        }

        setTraceId(result);
        return result;
    }

    public static String genericTraceId(HttpServletRequest req) {
        String result = getTraceId(req);
        setTraceId(result);
        return result;
    }

    public static String getTraceId() {
        return REQ_TRACE_ID_STORE.get();
    }

    public static String getTraceId(HttpServletRequest req) {
        String traceId = req.getHeader(TRACE_ID_KEY);
        return StringUtils.isNotBlank(traceId)
                ? traceId
                : getTraceId();
    }

    public static void setTraceId(String traceId) {
        REQ_TRACE_ID_STORE.set(traceId);
    }

    public static void setTraceId(HttpServletRequest req) {
        setTraceId(getTraceId(req));
    }
}
