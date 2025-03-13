package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Builder
@Getter
@Setter
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value=1, message = "USER_ID_REQUIRED")
    private Long orderId;

    @Min(value=1, message = "PRODUCT_ID_REQUIRED")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value=0, message = "PRICE_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0")
    private Float price;

    @Min(value=1, message = "NUMBER_OF_PRODUCTS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_1")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @Min(value=0, message = "TOTAL_MONEY_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0")
    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;
}
