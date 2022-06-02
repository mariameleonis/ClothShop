package com.epam.clothshop.service;

import com.epam.clothshop.dao.OrderRepository;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.OrderItem;
import com.epam.clothshop.model.OrderStatus;
import com.epam.clothshop.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper orderItemMapper;

    @InjectMocks
    OrderServiceImpl orderService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testGetAllOrders() {

        when(orderRepository.findAll()).thenReturn(ORDER_LIST);

        List<Order> resultOrders = orderService.getOrders();

        assertEquals(resultOrders, ORDER_LIST);
    }

    @Test
    public void testGetOrderById_WhenEverythingIsOk() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_1));

        Order resultOrder = orderService.getOrderById(ORDER_1.getOrderId());

        assertEquals(resultOrder, ORDER_1);
    }

    @Test
    public void testGetOrderById_WhenNotFound() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById((long) Math.random());
        });
    }

    @Test
    public void testDeleteOrderById_WhenEverythingIsOk() {

        Long id = ORDER_1.getOrderId();

        when(orderRepository.findById(id)).thenReturn(Optional.of(ORDER_1));

        orderService.deleteOrderById(id);

        verify(orderRepository).deleteById(id);
    }

    @Test
    public void testDeleteOrderById_WhenNotFound() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrderById((long) Math.random());
        });
    }

    @Test
    public void testDeleteOrderItem_WhenEverythingIsOk() {

        Order order = new Order(15L, USER_1, new HashSet<>(Set.of(
                ORDER_ITEM_1, ORDER_ITEM_2, ORDER_ITEM_3
        )));

        int orderItemsSize = order.getOrderItems().size();

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));

        orderService.deleteOrderItem(order.getOrderId(), ORDER_ITEM_1.getId());

        assertEquals(orderItemsSize - 1, order.getOrderItems().size());
    }

    @Test
    public void testDeleteOrderItem_WhenOrderNotFound() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrderItem((long) Math.random(), (long) Math.random());
        });
    }

    @Test
    public void testDeleteOrderItem_WhenOrderDoesNotContainOrderItem() {

        Order order = new Order(15L, USER_1, Set.of(
                ORDER_ITEM_1, ORDER_ITEM_2, ORDER_ITEM_3
        ));

        when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrderItem(order.getOrderId(), 42L);
        });
    }

    @Test
    public void testFindOrderItemInOrderById_WhenEverythingIsOk() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_1));

        OrderItem resultOrderItem = orderService.findOrderItemInOrderById(ORDER_1.getOrderId(), ORDER_ITEM_1.getId());

        assertEquals(resultOrderItem, ORDER_ITEM_1);
    }

    @Test
    public void testFindOrderItemInOrderById_WhenOrderNotFound() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            OrderItem resultOrderItem = orderService.findOrderItemInOrderById(ORDER_1.getOrderId(),
                    ORDER_ITEM_1.getId());
        });
    }

    @Test
    public void testFindOrderItem_WhenOrderDoesNotContainOrderItem() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_1));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrderItem(ORDER_1.getOrderId(), 42L);
        });
    }

    @Test
    public void testAddOrderItemToOrder_WhenEverythingIsOk() {

        Order order = new Order(15L, USER_1, new HashSet<>());

        when(orderItemMapper.map(VALID_ORDER_ITEM_DTO, OrderItem.class))
                .thenReturn(modelMapper.map(VALID_ORDER_ITEM_DTO, OrderItem.class));

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.addOrderItem(order.getOrderId(), VALID_ORDER_ITEM_DTO);

        assertTrue(ORDER_3.getOrderItems().size() == 1);
    }

    @Test
    public void testAddOrderItemToOrder_WhenOrderNotFound() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.addOrderItem(ORDER_3.getOrderId(), VALID_ORDER_ITEM_DTO);
        });
    }

    @Test
    public void testAddOrderItemToOrder_WhenInvalidOrderItemDtoSupplied() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_3));

        assertThrows(Exception.class, () -> {
            orderService.addOrderItem(ORDER_3.getOrderId(), INVALID_ORDER_ITEM_DTO);
        });
    }

    @Test
    public void testCancelOrder_WhenEverythingIsOk() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_3));

        orderService.cancelOrderById(ORDER_3.getOrderId());

        assertTrue(ORDER_3.getStatus().equals(OrderStatus.CANCELLED.name()));
    }

    @Test
    public void testCancelOrder_WhenOrderNotFound() {

        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(Exception.class, () -> {
            orderService.cancelOrderById(ORDER_1.getOrderId());
        });
    }
}
