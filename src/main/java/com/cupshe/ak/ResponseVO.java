package com.cupshe.ak;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangjia
 */
@Data
public class ResponseVO<T> implements Serializable {
    private static final long serialVersionUID = 8780569239140576438L;

    private Boolean success;
    private Long timeStamp;
    private String retCode;
    private String retInfo;
    private T data;

    private ResponseVO() {
        this.setSuccess(Boolean.TRUE);
        this.setTimeStamp(System.currentTimeMillis());
        this.setRetCode("000000");
        this.setRetInfo("success");
    }

    public static ResponseVO<Object> of() {
        return new ResponseVO<>();
    }

    public static <T> ResponseVO<T> of(T data) {
        ResponseVO<T> result = new ResponseVO<>();
        result.setData(data);
        return result;
    }

    public static <T> ResponseVO<RetData<T>> of(List<T> list) {
        ResponseVO<RetData<T>> result = new ResponseVO<>();
        result.setData(RetData.of(list));
        return result;
    }

    public static ResponseVO<Exception> of(Exception e) {
        ResponseVO<Exception> result = new ResponseVO<>();
        result.setSuccess(Boolean.FALSE);
        result.setRetCode("000001");
        result.setRetInfo(defaultErrorMessage(e.getMessage()));
        return result;
    }

    public static ResponseVO<String> of(String retCode, String retInfo) {
        ResponseVO<String> result = new ResponseVO<>();
        result.setSuccess(Boolean.FALSE);
        result.setRetCode(retCode);
        result.setRetInfo(retInfo);
        return result;
    }

    public static ResponseVO<String> of(String retCode, String retInfo, String data) {
        ResponseVO<String> result = of(retCode, retInfo);
        result.setData(data);
        return result;
    }

    private static String defaultErrorMessage(String message) {
        return message != null ? message : "Server error";
    }

    @Data
    public static class RetData<T> {
        private List<T> list;

        static <T> RetData<T> of(List<T> list) {
            RetData<T> result = new RetData<>();
            result.setList(list);
            return result;
        }
    }
}
