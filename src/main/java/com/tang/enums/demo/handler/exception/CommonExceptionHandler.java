package com.tang.enums.demo.handler.exception;

import com.tang.enums.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * author: tangj <br>
 * date: 2019-04-09 11:46 <br>
 * description:
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> methodNotSupported(Throwable e) {
        e.printStackTrace();
        log.debug("服务器异常", e);
        return new Result<>(500, e.getMessage());
    }
}
