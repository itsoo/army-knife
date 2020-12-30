package com.cupshe.ak.common;

import com.cupshe.ak.net.TraceIdUtils;
import com.cupshe.ak.request.RequestHeaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.cupshe.ak.common.BaseConstant.*;

/**
 * AutoRequestCachedConfigurer
 *
 * @author zxy
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ServletComponentScan
@WebFilter(filterName = "request-filter", urlPatterns = "/*")
public class AutoRequestCachedConfigurer implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        REQ_TRACE_ID_STORE.set(TraceIdUtils.getTraceId((HttpServletRequest) req));
        REQ_HEADERS_STORE.set(RequestHeaderUtils.getRequestHeaders((HttpServletRequest) req));
        REQ_PARAMS_STORE.set(req.getParameterMap());
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        REQ_TRACE_ID_STORE.remove();
        REQ_HEADERS_STORE.remove();
        REQ_PARAMS_STORE.remove();
    }
}