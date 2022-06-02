package com.epam.clothshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private String status = OrderStatus.PLACED.name();

    private Boolean complete = false;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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

    public boolean remove(Long orderItemId) {

        if (orderItems != null) {

            for (OrderItem oi : orderItems) {
                if(oi.getId() == orderItemId)
                    return orderItems.remove(oi);
            }
        }

        return false;
    }

    public BigDecimal getTotalPrice() {
        return orderItems.stream()
                .map(OrderItem::getSellingPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Tolerate
    public Order(Long id, User user, Set<OrderItem> orderItems) {
        this.orderId = id;
        this.user = user;
        this.orderItems = orderItems;
    }
}
