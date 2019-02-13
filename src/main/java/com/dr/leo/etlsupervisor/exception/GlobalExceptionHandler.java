package com.dr.leo.etlsupervisor.exception;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:48
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public RestResponseResult<String> serviceExceptionHandler(ServiceException e) {
        return RestResponseResult.failed(e.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResponseResult<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        return RestResponseResult.failed("你把系统玩炸了，开发人员正在火速救场！");
    }


}
