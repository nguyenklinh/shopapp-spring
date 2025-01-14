package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.dtos.OrderDTO;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.services.OrderService;
import com.nguyenklinh.shopapp.validation.UpdateOrderValidationGroup;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
//http://localhost:8083/v1/api/orders
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(order)
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Delete order successfully")
                .build());
    }

    @GetMapping("user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long id){
        List<Order> orders = orderService.findByUserId(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orders)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id,
                                         @Validated(UpdateOrderValidationGroup.class) @RequestBody OrderDTO orderDTO) {
        Order order = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(order)
                .build());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(order)
                .build());
    }
}
