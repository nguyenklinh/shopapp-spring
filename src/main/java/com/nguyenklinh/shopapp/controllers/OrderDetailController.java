package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.dtos.OrderDetailDTO;
import com.nguyenklinh.shopapp.models.OrderDetail;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.services.OrderDetailService;
import com.nguyenklinh.shopapp.validation.UpdateOrderDetailValidationGroup;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
//http://localhost:8083/api/v1/order-details
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orderDetail)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail( @PathVariable("id") Long id)  {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orderDetail)
                .build());
    }
    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("order_id") Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orderDetails)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Delete order detail successfully")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id,
                                               @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orderDetail)
                .build());
    }
}
