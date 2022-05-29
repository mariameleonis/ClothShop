package com.epam.clothshop.dto;

import com.epam.clothshop.model.OrderItem;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {

    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=4, fraction=2)
    private BigDecimal totalPrice;

    @NotNull
    private Set<OrderItem> orderItems;
}
