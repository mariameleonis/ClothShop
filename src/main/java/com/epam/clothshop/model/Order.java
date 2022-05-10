package com.epam.clothshop.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private LocalDate shipDate;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDate createdAt;

    @Column(length = 30, insertable = false)
    private String status = "PLACED";

    @Column(insertable = false)
    private Boolean complete = false;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="total_price", nullable = false)
    private BigDecimal totalPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Set<OrderItem> orderItems;
}
