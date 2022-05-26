package com.epam.clothshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@ToString(exclude = "orderIds")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false, unique = true)
    private String username;

    @Column(name="first_name", length=25, nullable = false)
    private String firstName;

    @Column(name="last_name", length=25, nullable = false)
    private String lastName;

    @Column(length=50, nullable = false, unique = true)
    private String email;

    @Column(length=32, nullable = false)
    private String password;

    @Column(length=16, nullable = false, unique = true)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<Order> orders;
}
