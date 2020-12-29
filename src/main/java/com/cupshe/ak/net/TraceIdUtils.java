package com.cupshe.ak.net;

import com.cupshe.ak.common.BaseConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * TraceIdUtils
 *
 * @author zxy
 */
public class TraceIdUtils {

    public static String getTraceId() {
        return BaseConstant.REQ_TRACE_ID_STORE.get();
    }

    public static String getTraceId(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(BaseConstant.TRACE_ID_KEY))
                .orElse(getTraceId());
    }
}
