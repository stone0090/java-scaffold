package com.example.demo.api.protocal;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResult implements Serializable {

    private Boolean success;
    private Object data;
    private String errorMessage;
    private String errorCode;
    //    private Integer showType;
    //    private String traceId;
    //    private String host;

    public RestResult(Boolean success, Object data, String errorCode, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static RestResult success() {
        return success(null);
    }

    public static RestResult success(Object data) {
        return new RestResult(true, data, ResultCodeEnum.SUCCESS.getCode(), null);
    }

    public static RestResult failure(String errorCode, String errorMessage) {
        return new RestResult(false, null, errorCode, errorMessage);
    }

    public static RestResult failure(String errorCode, String errorMessage, String data) {
        return new RestResult(false, data, errorCode, errorMessage);
    }

    public static RestResult failure(String errorMessage) {
        return RestResult.failure(ResultCodeEnum.CUSTOM_ERROR.getCode(), errorMessage);
    }

}
