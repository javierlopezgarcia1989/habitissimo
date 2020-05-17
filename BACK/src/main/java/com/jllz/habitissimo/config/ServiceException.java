package com.jllz.habitissimo.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends Exception {
    private Integer httpStatus;
    private String message;
    private Integer code;
    private String developerMessage;

    private static final long serialVersionUID = -528134378438377740L;

    public ServiceException(Integer httpStatus, String message, Integer code,String developerMessage) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
        this.developerMessage=developerMessage;
    }

    public ServiceException(Integer httpStatus, String message, Integer code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }

    public ServiceException(ErrorMessage errorMessage){
        this.httpStatus = errorMessage.getHttpStatus();
        this.message = errorMessage.getMessage();
        this.code = errorMessage.getCode();
        this.developerMessage=errorMessage.getDeveloperMessage();
    }
}
