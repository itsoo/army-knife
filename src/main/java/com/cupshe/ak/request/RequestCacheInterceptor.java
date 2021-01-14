package com.cupshe.ak.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cupshe.ak.common.BaseConstant.*;
import static com.cupshe.ak.request.RequestHeaderUtils.getRequestHeader;
import static com.cupshe.ak.request.RequestHeaderUtils.getRequestHeaders;
import static com.cupshe.ak.request.RequestTraceIdUtils.genericTraceId;

/**
 * RequestCacheInterceptor
 *
 * @author zxy
 */
@Slf4j
public class RequestCacheInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        log.info("Request-filter [   set cache] ===> {}", req.getHeader(TOKEN_KEY));

        REQ_TRACE_ID_STORE.set(genericTraceId(req));
        REQ_HEADERS_STORE.set(getRequestHeaders(req));
        REQ_PARAMS_STORE.set(req.getParameterMap());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e) {
        log.info("Request-filter [remove cache] ===> {}", getRequestHeader(TOKEN_KEY));

        REQ_TRACE_ID_STORE.remove();
        REQ_HEADERS_STORE.remove();
        REQ_PARAMS_STORE.remove();
    }
}
