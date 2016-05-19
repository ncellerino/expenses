package com.expenses.exception;

import com.expenses.util.SmartMoneyConstants;

/**
 * Generic errors returned by lambda service to be mapped by AWS Api Gateway.
 */
public enum ErrorResponseEnum {

    INTERNAL_SERVER_ERROR("001", "Server is unavailable.", SmartMoneyConstants.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND("002", "Resource not found in database.", SmartMoneyConstants.NOT_FOUND),
    INVALID_CREDENTIALS("003", "Invalid username or password.", SmartMoneyConstants.UNAUTHORIZED),
    USER_ROLE_UNAUTHORIZED("004", "User lacks privilege", SmartMoneyConstants.UNAUTHORIZED),
    VALUE_ALREADY_EXISTS("005", "Value already exists", SmartMoneyConstants.CONFLICT),
    USER_NOT_FOUND("006", "User was not found", SmartMoneyConstants.BAD_REQUEST),
    INVALID_PARAM_VALUE("007", "Parameter value is invalid", SmartMoneyConstants.BAD_REQUEST)
    ;

    String code;
    String message;
    String httpStatusCode;

    private ErrorResponseEnum(String code, String message, String httpStatusCode) {
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getHttpStatus() {
        return httpStatusCode;
    }
}
