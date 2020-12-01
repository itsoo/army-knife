package com.cupshe.ak.common;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.net.TraceIdUtils;
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

import static com.cupshe.ak.common.BaseConstant.REQ_HEADERS_STORE;
import static com.cupshe.ak.common.BaseConstant.TRACE_ID_STORE;

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
            storeRequestTraceId(req);
            storeRequestHeaders(req);
            return true;
        }

        private void storeRequestTraceId(HttpServletRequest req) {
            String storeTraceId = TraceIdUtils.getTraceId(req);
            if (StringUtils.isNotBlank(storeTraceId)) {
                TRACE_ID_STORE.set(storeTraceId);
            }
        }

        private void storeRequestHeaders(HttpServletRequest req) {
            List<Kv> storeHeaders = new ArrayList<>();
            Enumeration<String> headerNames = req.getHeaderNames();
            for (String key; headerNames.hasMoreElements(); ) {
                key = headerNames.nextElement();
                storeHeaders.add(new Kv(key, req.getHeader(key)));
            }

            REQ_HEADERS_STORE.set(storeHeaders);
        }
    }
}
