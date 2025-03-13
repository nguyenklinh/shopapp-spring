package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordDTO {
    @NotBlank(message = "CURRENT_PASSWORD_BLANK")
    @JsonProperty("current_password")
    private String currentPassword;

    @NotBlank(message = "NEW_PASSWORD_BLANK")
    @JsonProperty("new_password")
    private String newPassword;

    @NotBlank(message = "CONFIRM_NEW_PASSWORD_BLANK")
    @JsonProperty("confirm_new_password")
    private String confirmNewPassword;
}
