package com.dr.leo.etlsupervisor.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:44
 */
@EqualsAndHashCode(callSuper = true)

public class ServiceException extends RuntimeException {
    protected String errorMsg;

    public ServiceException(String errorMsg) {
        this.errorMsg = errorMsg;
        setErrorMsg(String.format("温馨小提示：%s!", errorMsg));
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
