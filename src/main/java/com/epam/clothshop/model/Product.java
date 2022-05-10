package com.epam.clothshop.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
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
}
