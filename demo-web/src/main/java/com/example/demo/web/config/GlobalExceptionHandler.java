package com.example.demo.web.config;

import javax.validation.ConstraintViolationException;

import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.protocal.ResultCodeEnum;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author stone
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    RestResult handleRuntimeException(RuntimeException e) {
        return RestResult.failure(ResultCodeEnum.CUSTOM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    RestResult handleCustomException(Exception e) {
        //return RestResult.failure(ResultCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
        return RestResult.failure("系统繁忙，请稍后再试！");
    }

}
