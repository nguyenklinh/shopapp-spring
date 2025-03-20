package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.OrderDTO;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    List<Order> findByUserId(Long userId);
    Page<OrderResponse> searchOrders(String keyword, Pageable pageable);
}
