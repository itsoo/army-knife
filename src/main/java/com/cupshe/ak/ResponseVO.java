package com.cupshe.ak;

import com.cupshe.ak.io.IoUtils;
import com.cupshe.ak.json.JsonUtils;
import lombok.Data;
import lombok.SneakyThrows;

import javax.servlet.ServletOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wangjia
 */
@Data
public class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = 8780569239140576438L;

    private static final String SUCCESS_RET_CODE = "000000";
    private static final String SUCCESS_RET_INFO = "success";

    private Boolean success;
    private Long timeStamp;
    private String retCode;
    private String retInfo;
    private T data;

    private ResponseVO() {
        this.setTimeStamp(System.currentTimeMillis());
    }

    public static ResponseVO<Object> of() {
        return of(Boolean.TRUE, SUCCESS_RET_CODE, SUCCESS_RET_INFO, null);
    }

    public static ResponseVO<String> of(String retCode, String retInfo) {
        return of(Boolean.FALSE, retCode, retInfo, null);
    }

    public static ResponseVO<String> of(Boolean success, String retCode, String retInfo) {
        return of(success, retCode, retInfo, null);
    }

    public static <T> ResponseVO<RetData<T>> of(Boolean success, List<T> list) {
        return of(success, SUCCESS_RET_CODE, SUCCESS_RET_INFO, RetData.of(list));
    }

    public static <T> ResponseVO<RetData<T>> of(List<T> list) {
        return of(Boolean.TRUE, SUCCESS_RET_CODE, SUCCESS_RET_INFO, RetData.of(list));
    }

    public static <T> ResponseVO<RetData<T>> of(String retCode, String retInfo, List<T> list) {
        return of(Boolean.FALSE, retCode, retInfo, RetData.of(list));
    }

    public static ResponseVO<Exception> of(Exception e) {
        return of(Boolean.FALSE, "000001", defaultErrorMessage(e.getMessage()), null);
    }

    public static ResponseVO<Exception> of(Boolean success, Exception e) {
        return of(success, "000001", defaultErrorMessage(e.getMessage()), null);
    }

    public static ResponseVO<Exception> of(String retCode, Exception e) {
        return of(Boolean.FALSE, retCode, defaultErrorMessage(e.getMessage()), null);
    }

    public static ResponseVO<Exception> of(Boolean success, String retCode, Exception e) {
        return of(success, retCode, defaultErrorMessage(e.getMessage()), null);
    }

    public static <T> ResponseVO<T> of(String retCode, String retInfo, T data) {
        return of(Boolean.FALSE, retCode, retInfo, data);
    }

    public static <T> ResponseVO<T> of(T data) {
        return of(Boolean.TRUE, SUCCESS_RET_CODE, SUCCESS_RET_INFO, data);
    }

    public static <T> ResponseVO<T> of(Boolean success, T data) {
        return of(success, SUCCESS_RET_CODE, SUCCESS_RET_INFO, data);
    }

    public static <T> ResponseVO<T> of(Boolean success, String retCode, String retInfo, T data) {
        ResponseVO<T> result = new ResponseVO<>();
        result.setSuccess(success);
        result.setRetCode(retCode);
        result.setRetInfo(retInfo);
        result.setData(data);
        return result;
    }

    @SneakyThrows
    public static void write(ServletOutputStream os, Exception e) {
        IoUtils.write(os, JsonUtils.objectToJson(of(e)));
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
