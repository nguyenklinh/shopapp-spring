package com.nguyenklinh.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    @Builder.Default
    private int code = 1000;
    private boolean success;
    private String message;
    private T result;
}
