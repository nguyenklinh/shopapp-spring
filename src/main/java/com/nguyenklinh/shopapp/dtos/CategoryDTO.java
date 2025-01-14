package com.nguyenklinh.shopapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    @NotBlank(message = "CATEGORY_NAME_NOT_BLANK")
    private String name;
}
