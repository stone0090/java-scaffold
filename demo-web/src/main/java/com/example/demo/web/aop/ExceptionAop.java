package com.example.demo.web.aop;

import java.util.Iterator;

import javax.validation.ConstraintViolationException;

import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.protocal.ResultCodeEnum;
import com.example.demo.service.exception.CustomException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAop {

    /**
     * 统一处理 hibernate Validator 抛出来的参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody
    RestResult handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        e.getConstraintViolations().forEach(x -> message.append(x.getMessage()).append("\n"));
        return RestResult.failure(ResultCodeEnum.ARGUMENT_ERROR.getCode(), message.toString());
    }

    /**
     * 统一处理 hibernate Validator 抛出来的参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    RestResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            message.append(error.getDefaultMessage()).append("\n");
        }
        return RestResult.failure(ResultCodeEnum.ARGUMENT_ERROR.getCode(), message.toString());
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
