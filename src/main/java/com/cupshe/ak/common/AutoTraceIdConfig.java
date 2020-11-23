package com.cupshe.ak.common;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.cupshe.ak.common.BaseConstant.*;

/**
 * TraceIdInterceptor
 *
 * @author zxy
 */
@Slf4j
public class AutoTraceIdConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor())
                .addPathPatterns("/**");
    }

    public static class TraceIdInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
            storeRequestHeaders(req, new ArrayList<>());
            storeRequestParams(req, new ArrayList<>());
            storeTraceId(req);
            return true;
        }

        private void storeRequestHeaders(HttpServletRequest req, List<Kv> headers) {
            Enumeration<String> headerNames = req.getHeaderNames();
            for (String key; headerNames.hasMoreElements(); ) {
                key = headerNames.nextElement();
                headers.add(new Kv(key, req.getHeader(key)));
            }

            REQ_HEADERS_STORE.set(headers);
        }

        private void storeRequestParams(HttpServletRequest req, List<Kv> params) {
            Enumeration<String> paramNames = req.getParameterNames();
            for (String key; paramNames.hasMoreElements(); ) {
                key = paramNames.nextElement();
                params.add(new Kv(key, req.getParameter(key)));
            }

            REQ_PARAMS_STORE.set(params);
        }

        private void storeTraceId(HttpServletRequest req) {
            String traceId = req.getHeader(TRACE_ID_KEY);
            if (StringUtils.isNotBlank(traceId)) {
                TRACE_ID_STORE.set(traceId);
            }
        }
    }
}
