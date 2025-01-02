package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private String username;
    @JsonProperty("phone_number")
    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    private String phoneNumber;
    private String password;
}
