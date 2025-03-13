package com.nguyenklinh.shopapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Builder
@Getter
@Setter
public class CategoryDTO {
    @NotBlank(message = "CATEGORY_NAME_NOT_BLANK")
    private String name;
}
