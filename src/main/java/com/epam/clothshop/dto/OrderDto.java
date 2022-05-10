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
public class OrderDto extends Dto {

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Long categoryId;

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private LocalDate shipDate;

    @Null(groups = {New.class, Update.class})
    private LocalDate createdAt;

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private String status;

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Boolean complete;

    @Null(groups = {Update.class})
    @NotNull(groups = {New.class})
    private Long userId;

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    @DecimalMin(value = "0.0", inclusive = false, groups = {New.class, Update.class})
    @Digits(integer=4, fraction=2, groups = {New.class, Update.class})
    private BigDecimal totalPrice;

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Set<OrderItem> orderItems;
}
