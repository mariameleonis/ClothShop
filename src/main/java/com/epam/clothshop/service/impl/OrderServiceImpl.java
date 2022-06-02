package com.epam.clothshop.service.impl;

import com.epam.clothshop.dao.OrderRepository;
import com.epam.clothshop.dto.OrderItemDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.OrderItem;
import com.epam.clothshop.model.OrderStatus;
import com.epam.clothshop.service.OrderService;
import lombok.experimental.Tolerate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id: '%s' not found", id)));
    }

    @Override
    public void deleteOrderById(Long id) {

        orderRepository.deleteById(getOrderById(id).getOrderId());
    }

    @Override
    @Transactional
    public void deleteOrderItem(long orderId, long orderItemId) {

        if(!getOrderById(orderId).remove(orderItemId))
            throw new ResourceNotFoundException(String.format("Order item with id: '%s' not found in order with id: '%s'", orderItemId, orderId));
    }

    @Override
    public OrderItem findOrderItemInOrderById(long orderId, long orderItemId) {

        return getOrderById(orderId).getOrderItems()
                .stream()
                .filter(oi -> oi.getId() == orderItemId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order item with id: '%s' not found in order with id: '%s'", orderItemId, orderId)));
    }

    @Override
    @Transactional
    public void addOrderItem(Long id, OrderItemDto orderItemDto) {

        getOrderById(id).add(modelMapper.map(orderItemDto, OrderItem.class));
    }

    @Override
    @Transactional
    public void cancelOrderById(long id) {

        getOrderById(id).setStatus(OrderStatus.CANCELLED.name());
    }
}
