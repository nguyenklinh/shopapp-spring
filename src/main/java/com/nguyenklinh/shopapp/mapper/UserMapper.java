package com.nguyenklinh.shopapp.mapper;

import com.nguyenklinh.shopapp.dtos.OrderDetailDTO;
import com.nguyenklinh.shopapp.dtos.UserDTO;
import com.nguyenklinh.shopapp.models.OrderDetail;
import com.nguyenklinh.shopapp.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
    UserDTO toUserDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserDTO userDTO);
}
