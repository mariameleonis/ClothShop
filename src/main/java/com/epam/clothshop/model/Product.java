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

    @Lob
    @Column(nullable = true, columnDefinition = "BLOB")
    private byte[] photo;

    @Column(name="category_id", nullable = false)
    private Long categoryId;

    @Column(name="vendor_id", nullable = false)
    private Long vendorId;

    @Tolerate
    public Product(Long id, String name, BigDecimal price, Long categoryId, Long vendorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
    }
}
