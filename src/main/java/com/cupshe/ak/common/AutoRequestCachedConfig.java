package com.cupshe.ak.common;

import com.cupshe.ak.net.TraceIdUtils;
import com.cupshe.ak.request.RequestHeaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cupshe.ak.common.BaseConstant.*;

/**
 * AutoRequestCachedConfig
 *
 * @author zxy
 */
@Slf4j
public class AutoRequestCachedConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestCachedInterceptor()).addPathPatterns("/**");
    }

    /**
     * RequestCachedInterceptor
     */
    public static class RequestCachedInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
            REQ_TRACE_ID_STORE.set(TraceIdUtils.getTraceId(req));
            REQ_HEADERS_STORE.set(RequestHeaderUtils.getRequestHeaders(req));
            REQ_PARAMS_STORE.set(req.getParameterMap());
            return true;
        }

        @Override
        public void afterCompletion(
                HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

            REQ_TRACE_ID_STORE.remove();
            REQ_HEADERS_STORE.remove();
            REQ_PARAMS_STORE.remove();
        }
    }
}
