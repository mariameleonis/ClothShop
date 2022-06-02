package com.epam.clothshop.dto;

import com.epam.clothshop.model.OrderItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class OrderDto {

    @NotNull
    private Set<OrderItem> orderItems;
}
