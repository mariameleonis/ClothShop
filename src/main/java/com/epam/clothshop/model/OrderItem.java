package com.epam.clothshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @ManyToOne
    @JoinColumn(name="order_id")
    @JsonBackReference
    private Order order;

    private int quantity;

    @Column(name="selling_price")
    private BigDecimal sellingPrice;

    @Tolerate
    public OrderItem(Long id, Long productId, int quantity, BigDecimal sellingPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
    }
}
