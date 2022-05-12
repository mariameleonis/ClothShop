package com.epam.clothshop.model;

import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = true)
    private String imageFileName;

    @Column(name="units_in_stock", nullable = false)
    private int unitsInStock;

    @Column(name="category_id", nullable = false)
    private Long categoryId;

    @Column(name="vendor_id", nullable = false)
    private Long vendorId;

    @Tolerate
    public Product(Long id, String name, BigDecimal price, int unitsInStock, Long categoryId, Long vendorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitsInStock = unitsInStock;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
    }
}
