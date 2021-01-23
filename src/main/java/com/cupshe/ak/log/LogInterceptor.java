package com.cupshe.ak.log;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.request.RequestTraceIdUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : chenzuofu
 * @date : 2020-05-25
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e) {
        // 删除SessionId
        MDC.remove(BaseConstant.MDC_SESSION_KEY);
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        // 设置SessionId
        MDC.put(BaseConstant.MDC_SESSION_KEY, RequestTraceIdUtils.genericTraceId());
        return true;
    }
}
