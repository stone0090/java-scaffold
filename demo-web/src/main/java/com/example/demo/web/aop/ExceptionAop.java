package com.example.demo.web.aop;

import com.example.demo.api.protocal.RestResult;
import com.example.demo.service.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAop {

    @ExceptionHandler(CustomException.class)
    public @ResponseBody
    RestResult handleCustomException(CustomException e) {
        return RestResult.failure(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    RestResult handleCustomException(Exception e) {
        return RestResult.failure(e.getMessage());
//        return RestResult.failure("系统繁忙，请稍后再试！");
    }

}
