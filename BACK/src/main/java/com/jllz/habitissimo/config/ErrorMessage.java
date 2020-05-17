package com.jllz.habitissimo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage implements Serializable {
    private Integer httpStatus;
    private String message;
    private Integer code;
    private String developerMessage;

    private static final long serialVersionUID = 5318063708359922770L;

    public ErrorMessage() {
    }

    public ErrorMessage(ServiceException ex) {
        this.httpStatus = ex.getHttpStatus();
        this.message = ex.getMessage();
        this.code = ex.getCode();
    }

    public ErrorMessage(Integer httpStatus, String message, Integer code) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }

}
