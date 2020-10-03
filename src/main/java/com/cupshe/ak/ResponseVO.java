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
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setData(data);
        return responseVO;
    }

    public static <T> ResponseVO<RetData<T>> of(List<T> list) {
        ResponseVO<RetData<T>> responseVO = new ResponseVO<>();
        responseVO.setData(RetData.of(list));
        return responseVO;
    }

    public static ResponseVO<Exception> of(Exception e) {
        ResponseVO<Exception> responseVO = new ResponseVO<>();
        responseVO.setSuccess(Boolean.FALSE);
        responseVO.setRetCode("000001");
        responseVO.setRetInfo(defaultErrorMessage(e.getMessage()));
        return responseVO;
    }

    public static ResponseVO<String> of(String retCode, String retInfo) {
        ResponseVO<String> responseVO = new ResponseVO<>();
        responseVO.setSuccess(Boolean.FALSE);
        responseVO.setRetCode(retCode);
        responseVO.setRetInfo(retInfo);
        return responseVO;
    }

    private static String defaultErrorMessage(String msg) {
        return msg != null ? msg : "Server error";
    }

    @Data
    public static class RetData<T> {
        private List<T> list;

        static <T> RetData<T> of(List<T> list) {
            final RetData<T> result = new RetData<>();
            result.setList(list);
            return result;
        }
    }
}
