package com.cy.community.exception;

/**
 * @author cy
 * @since 2019-11-02 9:26
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public CustomizeException(ICustomerErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
