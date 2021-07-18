package com.example.demo.service.aop;

import java.util.Set;

import javax.annotation.PostConstruct;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author stone
 * @date 2021/07/18
 */
@Aspect
@Component
public class ValidatorAop {

    private Validator validator;

    @PostConstruct
    public void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Pointcut("execution(* com.example.demo.service.service.impl.*.*(..))")
    public void invoke() { }

    @Before("invoke()")
    public void validate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder message = new StringBuilder();
        for (Object arg : args) {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(arg);
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                message.append(constraintViolation.getMessage()).append("\n");
            }
        }
        if (!StringUtils.isEmpty(message.toString())) {
            throw new IllegalArgumentException(message.toString());
        }
    }

}
