package com.cupshe.ak.common;

/**
 * BaseConstant
 *
 * @author zxy
 */
public class BaseConstant {

    /*** trace-id store */
    public static final InheritableThreadLocal<String> TRACE_ID_STORE = new InheritableThreadLocal<>();

    /*** trace-id key */
    public static final String TRACE_ID_KEY = "Trace-ID";

    /*** session-id */
    public static final String SESSION_KEY = "sessionId";

    /*** 身份认证字段 */
    public static final String AUTHORIZATION = "Authorization";

    /*** 调用来源头部标识 */
    public static final String CALL_SOURCE_KEY = "Call-Source";

    /*** 调用来源头部标识 */
    public static final String CALL_SOURCE_VALUE = "REST-CLIENT";

    /*** HTTP 请求协议 */
    public static final String PROTOCOL = "http://";
}
