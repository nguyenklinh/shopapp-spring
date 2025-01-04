package com.nguyenklinh.shopapp.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
    private int currentPage;
    private Long totalElements;
}
