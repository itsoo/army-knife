package com.cupshe.ak;

/**
 * BaseConstant
 *
 * @author zxy
 */
public class BaseConstant {

    /*** trance id store */
    public static final ThreadLocal<String> TRANCE_ID_STORE = new ThreadLocal<>();

    /*** trance id key */
    public static final String TRANCE_ID_KEY = "Trance-ID";

    /*** 调用来源头部标识 */
    public static final String CALL_SOURCE_KEY = "Call-Source";

    /*** 调用来源头部标识 */
    public static final String CALL_SOURCE_VALUE = "REST-CLIENT";

    /*** HTTP 请求协议 */
    public static final String PROTOCOL = "http://";
}
