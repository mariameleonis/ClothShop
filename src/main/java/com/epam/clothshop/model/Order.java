package com.epam.clothshop.model;

import lombok.*;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
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

    private LocalDate shipDate = LocalDate.now().plusDays(3L);

    @CreationTimestamp
    private LocalDate createdAt;

    @Column(length = 30)
    private String status = "PLACED";

    private Boolean complete = false;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="total_price", nullable = false)
    private BigDecimal totalPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();

    public void add(OrderItem orderItem) {

        if(orderItem != null) {
            if(orderItems == null) {
                orderItems = new HashSet<>();
            }

            orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }

    @Tolerate
    public Order(User user, BigDecimal totalPrice, Set<OrderItem> orderItems) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }
}
