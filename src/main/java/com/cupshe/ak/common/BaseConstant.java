package com.cupshe.ak.common;

import com.cupshe.ak.core.Kv;

import java.util.List;

/**
 * BaseConstant
 *
 * @author zxy
 */
public class BaseConstant {

    /*** trace-id store */
    public static final InheritableThreadLocal<String> TRACE_ID_STORE = new InheritableThreadLocal<>();

    /*** request headers store */
    public static final InheritableThreadLocal<List<Kv>> REQ_HEADERS_STORE = new InheritableThreadLocal<>();

    /*** request params store */
    public static final InheritableThreadLocal<List<Kv>> REQ_PARAMS_STORE = new InheritableThreadLocal<>();

    /*** trace-id key */
    public static final String TRACE_ID_KEY = "Trace-ID";

    /*** session-id */
    public static final String SESSION_KEY = "sessionId";

    /*** 身份认证字段 */
    public static final String AUTHORIZATION = "Authorization";

    /*** 身份认证字段 */
    public static final String TOKEN_KEY = "token";
}
