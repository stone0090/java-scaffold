package com.example.demo.web.aop;

import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.protocal.ResultCodeEnum;
import com.example.demo.service.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAop {

    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody
    RestResult handleIllegalArgumentException(IllegalArgumentException e) {
        return RestResult.failure(ResultCodeEnum.ARGUMENT_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public @ResponseBody
    RestResult handleCustomException(CustomException e) {
        return RestResult.failure(ResultCodeEnum.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    RestResult handleCustomException(Exception e) {
        return RestResult.failure(ResultCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
        //        return RestResult.failure("系统繁忙，请稍后再试！");
    }

}
