package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.dtos.OrderDetailDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.mapper.OrderDetailMapper;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.models.OrderDetail;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.repositorys.OrderDetailRepository;
import com.nguyenklinh.shopapp.repositorys.OrderRepository;
import com.nguyenklinh.shopapp.repositorys.ProductRepository;
import com.nguyenklinh.shopapp.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()->new MyException(ErrorCode.CAN_NOT_FIND_ORDER));

        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()->new MyException(ErrorCode.CAN_NOT_FIND_PRODUCT));

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(orderDetailDTO);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->new MyException(ErrorCode.CAN_NOT_FIND_ORDER_DETAIL));
        return orderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->new MyException(ErrorCode.CAN_NOT_FIND_ORDER_DETAIL));

        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()->new MyException(ErrorCode.CAN_NOT_FIND_ORDER));

        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()->new MyException(ErrorCode.CAN_NOT_FIND_PRODUCT));
        orderDetailMapper.updateOrderDetail(orderDetail,orderDetailDTO);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        if(!orderDetailRepository.existsById(id)){
            throw new MyException(ErrorCode.CAN_NOT_FIND_ORDER_DETAIL);
        }
        orderDetailRepository.deleteById(id);
    }


    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new MyException(ErrorCode.CAN_NOT_FIND_ORDER);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (orderDetails.isEmpty()) {
            throw new MyException(ErrorCode.ORDER_DETAILS_NOT_FOUND);
        }

        return orderDetails;
    }
}
