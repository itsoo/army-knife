package com.cupshe.ak.net;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.text.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * TraceIdUtils
 *
 * @author zxy
 */
public class TraceIdUtils {

    public static String getTraceId() {
        try {
            return BaseConstant.TRACE_ID_STORE.get();
        } finally {
            BaseConstant.TRACE_ID_STORE.remove();
        }
    }

    public static String getTraceId(HttpServletRequest req) {
        String result = req.getHeader(BaseConstant.TRACE_ID_KEY);
        if (StringUtils.isEmpty(result)) {
            return getTraceId();
        }

        return result;
    }
}
