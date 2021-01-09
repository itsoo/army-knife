package com.cupshe.ak.enums;

/**
 * Description: TODO
 * User: chenzuofu
 * Date: 2020-11-10
 * Time: 下午2:19
 */
public enum ExceptionEnums {
    //未登录
    NOTLOGIN("CS100001","user not login"),
    TOKENEXPIRED("CS100002","token is exipred"),
    TOKENERROR("CS100003","token has exception");


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
