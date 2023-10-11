package com.catGod.exception;

import com.catGod.commons.BaseResponse;
import com.catGod.commons.ResponseUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by lily via on 2023/10/7 17:55
 */
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public <T> BaseResponse<T> controllerException(UserException e) {
        return ResponseUtil.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public <T> BaseResponse<T> errorException(String code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }
}
