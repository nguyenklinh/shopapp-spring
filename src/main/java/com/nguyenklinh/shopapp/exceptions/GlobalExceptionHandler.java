package com.nguyenklinh.shopapp.exceptions;

import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.components.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageUtil messageUtil;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException e){
        String enumKey = e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException e) {
        ErrorCode errorCode = ErrorCode.UNSUPPORTED_MEDIA_TYPE;
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUsernameNotFound(UsernameNotFoundException e) {
        return buildResponse(ErrorCode.USERNAME_NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(value = MyException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(MyException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        String message;
        try {
            // X? l� message v?i getParameter
            message = (exception.getParameter() != null && exception.getParameter().length > 0) ?
                    messageUtil.getMessage(errorCode.getMessage(), exception.getParameter()) :
                    messageUtil.getMessage(errorCode.getMessage());
        } catch (Exception e) {
            // Fallback n?u c� l?i khi format message
            message = errorCode.getMessage();
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(message);

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .success(false)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e) {
        return buildResponse(ErrorCode.UNCATEGORIZED_EXCEPTION, e.getMessage());
    }

    private ResponseEntity<ApiResponse<?>> buildResponse(ErrorCode errorCode, String details) {
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .success(false)
                        .message(messageUtil.getMessage(errorCode.getMessage()) + (details != null ? ": " + details : ""))
                        .build());
    }
}
