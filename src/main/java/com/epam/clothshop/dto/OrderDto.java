package com.epam.clothshop.dto;

import com.epam.clothshop.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    @NotNull
    private Set<OrderItem> orderItems;
}
