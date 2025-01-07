package com.nguyenklinh.shopapp.mapper;

import com.nguyenklinh.shopapp.dtos.OrderDTO;
import com.nguyenklinh.shopapp.dtos.OrderDetailDTO;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.models.OrderDetail;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderDetail(@MappingTarget OrderDetail orderDetail, OrderDetailDTO orderDetailDTO);
}
