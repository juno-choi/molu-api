package com.molu.molu.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Slf4j
@Aspect
@Component
public class ValidAdvice {

    @Around(value = "execution(* com.molu.molu.controller..*.*(..))")
    public Object validAdviceHandler(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            BindingResult bindingResult = getBindingResult(arg);
            if(bindingResult == null) continue;
            if(bindingResult.hasErrors()){
                FieldError error = bindingResult.getFieldErrors().get(0);
                String errorMessage = error.getDefaultMessage();
                throw new IllegalArgumentException(errorMessage);
            }
        }
        return pjp.proceed();
    }

    private BindingResult getBindingResult(Object arg) {
        BindingResult bindingResult = null;
        if(arg instanceof BindingResult){
            bindingResult = (BindingResult) arg;
        }
        return bindingResult;
    }
}
