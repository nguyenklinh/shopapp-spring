package com.nguyenklinh.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nguyenklinh.shopapp.validation.UpdateOrderValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "USER_ID_REQUIRED")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

    @Email(message = "EMAIL_INVALID", groups = {UpdateOrderValidationGroup.class})
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "PHONE_NUMBER_NOT_BLANK")
    @Size(min = 10, message = "PHONE_NUMBER_SIZE_REQUIRED")
    private String phoneNumber;

    @NotBlank(message = "ADDRESS_NOT_BLANK")
    private String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("cart_items")
    private List<CartItemDTO> cartItems;
}
