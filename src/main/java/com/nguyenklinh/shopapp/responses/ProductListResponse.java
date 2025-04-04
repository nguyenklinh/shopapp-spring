package com.nguyenklinh.shopapp.responses;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
    private int currentPage;
    private Long totalElements;
}
