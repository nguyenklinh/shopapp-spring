package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
public class ProductDTO {
    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    private String name;

    @Min(value = 0, message = "PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0")
    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;
}
