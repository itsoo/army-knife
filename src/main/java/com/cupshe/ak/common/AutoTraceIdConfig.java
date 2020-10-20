package com.cupshe.ak.common;

import com.cupshe.ak.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.cupshe.ak.common.BaseConstant.TRACE_ID_KEY;
import static com.cupshe.ak.common.BaseConstant.TRACE_ID_STORE;

/**
 * TraceIdInterceptor
 *
 * @author zxy
 */
@Configuration
@Slf4j
public class AutoTraceIdConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor())
                .addPathPatterns("/**");
    }

    public class TraceIdInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
            String traceId = req.getHeader(TRACE_ID_KEY);
            if (StringUtils.isNotBlank(traceId)) {
                TRACE_ID_STORE.set(traceId);
            }

            return true;
        }
    }
}
