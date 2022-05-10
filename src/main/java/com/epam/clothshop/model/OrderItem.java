package com.epam.clothshop.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Entity
@Data
@IdClass(OrderItemId.class)
public class OrderItem {

    @Id
    private Long productId;

    @Id
    private Long orderId;

    @Column(nullable = false)
    private int quantity;

    @Column(name="selling_price", nullable = false)
    private BigDecimal sellingPrice;
}
