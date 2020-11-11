package com.cupshe.ak.enums;

/**
 * Description: TODO
 * User: chenzuofu
 * Date: 2020-11-10
 * Time: 下午2:19
 */
public enum ExceptionEnums {
    //未登录
    NOTLOGIN("CS100001","用户未登录"),
    TOKENEXPIRED("CS100002","用户token信息过期"),
    TOKENERROR("CS100003","用户token信息异常");


    /**
     * 异常code
     */
    private final String errCode;
    /**
     * 报错信息
     */
    private final String errorMessage;

    ExceptionEnums(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errorMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
