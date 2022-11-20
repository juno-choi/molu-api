package com.molu.molu.common.aop;

import com.molu.molu.domain.error.Error;
import com.molu.molu.domain.error.ValidationError;
import com.molu.molu.exception.ListException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aspect
@Component
public class ValidAdvice {

    @Around(value = "execution(* com.molu.molu.controller..*.*(..))")
    public Object validAdviceHandler(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        List<Error> errorList = new ArrayList<>();
        for (Object arg : args) {
            BindingResult bindingResult = getBindingResult(arg);
            if(bindingResult == null) continue;
            if(bindingResult.hasErrors()){
                bindingResult.getFieldErrors().forEach(
                        fieldError -> errorList.add(
                                ValidationError.builder()
                                        .field(fieldError.getField())
                                        .message(fieldError.getDefaultMessage())
                                        .build()
                        )
                );
            }
        }
        if(!errorList.isEmpty()){
            throw new ListException("Validation Exception", errorList);
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
