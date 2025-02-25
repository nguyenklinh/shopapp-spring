package com.nguyenklinh.shopapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname",length = 100)
    private String fullName;

    @Column(length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(length = 100)
    private String note;

    @Column(name = "order_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date orderDate;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "shipping_method", length = 100)
    private String shippingMethod;

    @Column(name = "shipping_address", length = 200)
    private String shippingAddress;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "tracking_code", length = 100)
    private String trackingCode;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "status", length = 50)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
