package com.epam.clothshop.service;

import com.epam.clothshop.dto.OrderItemDto;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.OrderItem;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();
    Order getOrderById(Long id);
    void deleteOrderById(Long id);
    void deleteOrderItem(long orderId, long orderItemId);
    OrderItem findOrderItemInOrderById(long orderId, long orderItemId);
    void addOrderItem(Long id, OrderItemDto orderItemDto);
    void cancelOrderById(long id);
}
