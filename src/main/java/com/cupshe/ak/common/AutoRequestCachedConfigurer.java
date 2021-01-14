package com.cupshe.ak.common;

import com.cupshe.ak.request.RequestHeaderUtils;
import com.cupshe.ak.request.RequestTraceIdUtils;
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
@ServletComponentScan
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "request-filter", urlPatterns = "/*")
public class AutoRequestCachedConfigurer implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        log.info("Request-filter [doFilter] ===> " + (req.getParameter(TOKEN_KEY)));

        destroy();

        REQ_TRACE_ID_STORE.set(RequestTraceIdUtils.genericTraceId((HttpServletRequest) req));
        REQ_HEADERS_STORE.set(RequestHeaderUtils.getRequestHeaders((HttpServletRequest) req));
        REQ_PARAMS_STORE.set(req.getParameterMap());
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        log.info("Request-filter [destroy] ===> " + (REQ_HEADERS_STORE.get().get(TOKEN_KEY)));

        REQ_TRACE_ID_STORE.remove();
        REQ_HEADERS_STORE.remove();
        REQ_PARAMS_STORE.remove();
    }
}
