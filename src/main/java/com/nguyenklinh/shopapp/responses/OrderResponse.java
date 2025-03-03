package com.nguyenklinh.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.models.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder

public class OrderResponse {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("note")
    private String note;
    @JsonProperty("order_date")
    private Date orderDate;
    @JsonProperty("total_money")
    private float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    @JsonProperty("tracking_code")
    private String trackingCode;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("status")
    private String status;
    @JsonProperty("order_details")
    private List<OrderDetail> orderDetails;

    public static OrderResponse fromOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .totalMoney(order.getTotalMoney())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .shippingDate(order.getShippingDate())
                .trackingCode(order.getTrackingCode())
                .paymentMethod(order.getPaymentMethod())
                .isActive(order.isActive())
                .status(order.getStatus())
                .orderDetails(order.getOrderDetails())
                .build();
    }

    public static List<OrderResponse> fromOrderList(List<Order> orders) {
        return orders.stream().map(OrderResponse::fromOrder).toList();
    }

}
