package com.nguyenklinh.shopapp.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    CATEGORYID_NOT_EXISTED(1003, "Category id not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}",HttpStatus.BAD_REQUEST),
    UNSUPPORTED_MEDIA_TYPE(1009,"unsupported media type",HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    CREATE_PRODUCT_FAILED(1010,"Create product failed",HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_BLANK(1011,"Product name must not be blank",HttpStatus.BAD_REQUEST),
    PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0(1012,"Price must be greater than or equal to 0",HttpStatus.BAD_REQUEST),
    CAN_NOT_FIND_PRODUCT(1013,"Cannot find product with id",HttpStatus.NOT_FOUND),
    CATEGORY_ID_NOT_FOUND(1014,"Category id not found",HttpStatus.NOT_FOUND),;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
