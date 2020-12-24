package com.cupshe.ak.common;

import java.util.Map;

/**
 * BaseConstant
 *
 * @author zxy
 */
public class BaseConstant {

    /**
     * request trace-id store
     */
    public static final InheritableThreadLocal<String> REQ_TRACE_ID_STORE = new InheritableThreadLocal<>();

    /**
     * request headers store
     */
    public static final InheritableThreadLocal<Map<String, String>> REQ_HEADERS_STORE = new InheritableThreadLocal<>();

    /**
     * request params store
     */
    public static final InheritableThreadLocal<Map<String, String[]>> REQ_PARAMS_STORE = new InheritableThreadLocal<>();

    /**
     * trace-id key
     */
    public static final String TRACE_ID_KEY = "Trace-ID";

    /**
     * session-id key
     */
    public static final String SESSION_KEY = "JSESSIONID";

    /**
     * 身份认证字段
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 身份认证字段
     */
    public static final String TOKEN_KEY = "token";
}
