package com.nguyenklinh.shopapp.mapper;

import com.nguyenklinh.shopapp.dtos.OrderDTO;
import com.nguyenklinh.shopapp.models.Order;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);
    OrderDTO toOrderDTO(Order order);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrder(@MappingTarget Order order, OrderDTO orderDTO);
}
