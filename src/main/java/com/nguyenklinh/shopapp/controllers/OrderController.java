package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.dtos.OrderDTO;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.responses.OrderResponse;
import com.nguyenklinh.shopapp.services.OrderService;
import com.nguyenklinh.shopapp.components.MessageUtil;
import com.nguyenklinh.shopapp.utils.MessageKeys;
import com.nguyenklinh.shopapp.validation.UpdateOrderValidationGroup;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
//http://localhost:8083/v1/api/orders
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MessageUtil messageUtil;

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
                .message(messageUtil.getMessage(MessageKeys.SUCCESS_DELETED_ORDER))
                .build());
    }

    @GetMapping("user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long id){
        List<Order> orders = orderService.findByUserId(id);
        List<OrderResponse> orderResponses = OrderResponse.fromOrderList(orders);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orderResponses)
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        OrderResponse orderResponse = OrderResponse.fromOrder(order);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(orderResponse)
                .build());
    }
}
