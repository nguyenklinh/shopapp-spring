package com.nguyenklinh.shopapp.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderListResponse {
    private List<OrderResponse> orderResponses;
    private int totalPages;
    private int currentPage;
    private Long totalElements;
}
