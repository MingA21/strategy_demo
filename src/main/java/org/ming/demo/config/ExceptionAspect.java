package org.ming.demo.config;

import org.ming.demo.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  异常处理
 */
@RestControllerAdvice
public class ExceptionAspect {


    @ExceptionHandler(value = Throwable.class)
    public Result exception(Exception e) {
        e.printStackTrace();
        return Result.success(e.getMessage());
    }

}
