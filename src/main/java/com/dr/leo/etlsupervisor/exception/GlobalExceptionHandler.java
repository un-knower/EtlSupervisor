package com.dr.leo.etlsupervisor.exception;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:48
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public RestResponseResult<String> serviceExceptionHandler(ServiceException e) {
        logger.error(e.getErrorMsg());
        return RestResponseResult.failed(e.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    public RestResponseResult exceptionHandler(Exception e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return RestResponseResult.failed().message("你把系统玩炸了，开发人员正在火速救场！");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RestResponseResult validException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMsg = "参数校验异常！";
        if (null != fieldError) {
            errorMsg = fieldError.getDefaultMessage();
            logger.error("参数校验异常:{}({})", errorMsg, fieldError.getField());
        }
        return RestResponseResult.failed().message(errorMsg);
    }

    @ExceptionHandler({BindException.class})
    public RestResponseResult bindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMsg = "必填校验异常！";
        if (null != fieldError) {
            errorMsg = fieldError.getDefaultMessage();
            logger.error("必填校验异常:{}({})", errorMsg, fieldError.getField());
        }
        return RestResponseResult.failed().message(errorMsg);
    }
}
