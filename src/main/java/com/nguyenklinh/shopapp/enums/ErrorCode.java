package com.nguyenklinh.shopapp.enums;

import com.nguyenklinh.shopapp.utils.MessageKeys;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, MessageKeys.UNCATEGORIZED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1002, MessageKeys.USER_EXISTED, HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, MessageKeys.USER_NOT_EXISTED, HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, MessageKeys.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1007, MessageKeys.ACCESS_DENIED, HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, MessageKeys.INVALID_DOB,HttpStatus.BAD_REQUEST),
    UNSUPPORTED_MEDIA_TYPE(1009,MessageKeys.UNSUPPORTED_MEDIA_TYPE,HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    CREATE_PRODUCT_FAILED(1010,MessageKeys.PRODUCT_CREATE_FAILED,HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_BLANK(1011,MessageKeys.PRODUCT_NAME_BLANK,HttpStatus.BAD_REQUEST),
    PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0(1012,MessageKeys.PRICE_INVALID,HttpStatus.BAD_REQUEST),
    CAN_NOT_FIND_PRODUCT(1013,MessageKeys.PRODUCT_NOT_FOUND,HttpStatus.NOT_FOUND),
    CATEGORY_ID_NOT_FOUND(1014,MessageKeys.CATEGORY_NOT_FOUND,HttpStatus.NOT_FOUND),
    MAX_IMAGE_PER_PRODUCT(1015,MessageKeys.PRODUCT_MAX_IMAGES,HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE(1016,MessageKeys.FILE_TYPE_INVALID,HttpStatus.BAD_REQUEST),
    CAN_NOT_CREATE_UPLOAD_DIR(1017,MessageKeys.UPLOAD_DIR_CREATE,HttpStatus.INTERNAL_SERVER_ERROR),
    CAN_NOT_SAVE_FILE(1018,MessageKeys.FILE_SAVE,HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_SIZE_TOO_LARGE(1019,MessageKeys.FILE_SIZE_LARGE,HttpStatus.PAYLOAD_TOO_LARGE),
    PHONE_NUMBER_NOT_BLANK(1020,MessageKeys.PHONE_BLANK,HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_SIZE_REQUIRED(1021,MessageKeys.PHONE_SIZE,HttpStatus.BAD_REQUEST),
    USER_ID_REQUIRED(1022,MessageKeys.USER_ID_INVALID,HttpStatus.BAD_REQUEST),
    CAN_NOT_FIND_USER(1023,MessageKeys.USER_NOT_FOUND,HttpStatus.NOT_FOUND),
    INVALID_SHIPPING_DATE(1024,MessageKeys.SHIPPING_DATE_INVALID,HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_BLANK(1025,MessageKeys.ADDRESS_BLANK,HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1026,MessageKeys.EMAIL_INVALID,HttpStatus.BAD_REQUEST),
    CAN_NOT_FIND_ORDER(1027,MessageKeys.ORDER_NOT_FOUND,HttpStatus.NOT_FOUND),
    ORDER_ALREADY_PROCESSED(1028,MessageKeys.ORDER_PROCESSED,HttpStatus.BAD_REQUEST),
    PRODUCT_ID_REQUIRED(1029,MessageKeys.PRODUCT_ID_INVALID,HttpStatus.BAD_REQUEST),
    NUMBER_OF_PRODUCTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_1(1030,MessageKeys.PRODUCT_QUANTITY_INVALID,HttpStatus.BAD_REQUEST),
    TOTAL_MONEY_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0(1031,MessageKeys.TOTAL_MONEY_INVALID,HttpStatus.BAD_REQUEST),
    CAN_NOT_FIND_ORDER_DETAIL(1032,MessageKeys.ORDER_DETAIL_NOT_FOUND,HttpStatus.NOT_FOUND),
    ORDER_DETAILS_NOT_FOUND(1033,MessageKeys.ORDER_DETAILS_EMPTY,HttpStatus.NOT_FOUND),
    USERNAME_NOT_FOUND(1034,MessageKeys.USERNAME_NOT_FOUND,HttpStatus.NOT_FOUND),
    CAN_NOT_GET_AUTHENTICATION_MANAGER(1035,MessageKeys.AUTH_MANAGER,HttpStatus.INTERNAL_SERVER_ERROR),
    CAN_NOT_CREATE_SECURITY_FILTER_CHAIN(1036,MessageKeys.SECURITY_FILTER_CHAIN,HttpStatus.INTERNAL_SERVER_ERROR),
    CAN_NOT_FIND_ROLE(1023,MessageKeys.ROLE_NOT_FOUND,HttpStatus.NOT_FOUND),
    USERNAME_OR_PASSWORD_INVALID(1024, MessageKeys.LOGIN_INVALID, HttpStatus.UNAUTHORIZED),
    PASSWORD_NOT_MATCH(1025, MessageKeys.PASSWORD_NOT_MATCH, HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_BLANK(1026, MessageKeys.PASSWORD_BLANK, HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_NOT_BLANK(1027,MessageKeys.CATEGORY_NAME_BLANK, HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(1028, MessageKeys.FILE_NOT_FOUND, HttpStatus.NOT_FOUND),
    INVALID_FILE_NAME(1029, MessageKeys.INVALID_FILE_NAME, HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(1030, MessageKeys.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED),
    USER_ID_LOCKED(1031, MessageKeys.USER_ID_LOCKED, HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXIST(1032, MessageKeys.PHONE_NUMBER_EXISTED, HttpStatus.BAD_REQUEST),
    CURRENT_PASSWORD_INVALID(1033, MessageKeys.CURRENT_PASSWORD_INVALID, HttpStatus.BAD_REQUEST),
    CURRENT_PASSWORD_BLANK(1034, MessageKeys.CURRENT_PASSWORD_BLANK, HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_BLANK(1035, MessageKeys.NEW_PASSWORD_BLANK, HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_CONFIRM_BLANK(1036, MessageKeys.NEW_PASSWORD_CONFIRM_BLANK, HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
