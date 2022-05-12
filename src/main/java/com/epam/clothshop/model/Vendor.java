package com.epam.clothshop.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "productIds")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @Column(length = 25, nullable = false)
    private String vendorName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vendorId")
    private List<Product> products;

    @Tolerate
    public Vendor(Long vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }

    @Tolerate
    public Vendor(Long vendorId, String vendorName, List<Product> products) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.products = products;
    }
}
