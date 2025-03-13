package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Builder
@Getter
@Setter
public class CartItemDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("quantity")
    private Integer quantity;
}
