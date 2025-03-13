package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
public class ProductImageDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("image_url")
    private String imageUrl;
}
