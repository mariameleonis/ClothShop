package com.epam.clothshop.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
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
    private Set<Product> products;
}
