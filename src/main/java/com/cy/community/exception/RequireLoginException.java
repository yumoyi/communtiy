package com.cy.community.exception;

/**
 * @author cy
 * @since 2019-11-02 9:26
 */
public class RequireLoginException extends RuntimeException{
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public RequireLoginException(ICustomerErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
