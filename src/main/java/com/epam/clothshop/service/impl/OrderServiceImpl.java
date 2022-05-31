package com.epam.clothshop.service.impl;

import com.epam.clothshop.dto.OrderItemDto;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.OrderItem;
import com.epam.clothshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    @Override
    public void deleteOrderById(Long id) {
        getOrderById(id);
    }

    @Override
    public void deleteOrderItem(long orderId, long orderItemId) {

    }

    @Override
    public OrderItem findOrderItemInOrderById(long orderId, long orderItemId) {
        return null;
    }

    @Override
    public void addOrderItem(Long id, OrderItemDto orderItemDto) {

    }

    @Override
    public void cancelOrderById(long id) {

    }
}
