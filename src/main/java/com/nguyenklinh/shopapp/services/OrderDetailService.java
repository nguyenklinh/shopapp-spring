package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.OrderDetailDTO;
import com.nguyenklinh.shopapp.models.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetail getOrderDetail(Long id) ;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
