package com.epam.clothshop.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemId implements Serializable {

    private Long productId;
    private Long orderId;

}
