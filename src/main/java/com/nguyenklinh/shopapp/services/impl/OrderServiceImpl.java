package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.dtos.OrderDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.enums.OrderStatus;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.mapper.OrderMapper;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.models.User;
import com.nguyenklinh.shopapp.repositorys.OrderRepository;
import com.nguyenklinh.shopapp.repositorys.UserRepository;
import com.nguyenklinh.shopapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository  userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new MyException(ErrorCode.CAN_NOT_FIND_USER));

        Order order = orderMapper.toOrder(orderDTO);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(String.valueOf(OrderStatus.PENDING));
        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new MyException(ErrorCode.INVALID_SHIPPING_DATE);
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new MyException(ErrorCode.CAN_NOT_FIND_ORDER));
        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new MyException(ErrorCode.CAN_NOT_FIND_ORDER));

        orderMapper.updateOrder(order, orderDTO);
        if (orderDTO.getUserId() != null) {
            User user = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new MyException(ErrorCode.CAN_NOT_FIND_USER));
            order.setUser(user);
        }
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new MyException(ErrorCode.CAN_NOT_FIND_ORDER));
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
