package com.nguyenklinh.shopapp.exceptions;

import com.nguyenklinh.shopapp.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyException extends RuntimeException {
    public MyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    private ErrorCode errorCode;
}
