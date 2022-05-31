package com.epam.clothshop.rest;

import com.epam.clothshop.dto.OrderItemDto;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.OrderItem;
import com.epam.clothshop.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
    }

    @PostMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable("id") Long id) {
        orderService.cancelOrderById(id);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<Set<OrderItem>> getOrderItems(@PathVariable("id") Long id) {

        Order order = orderService.getOrderById(id);

        return ResponseEntity.ok(order.getOrderItems());
    }

    @GetMapping("/{oid}/items/{iid}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable("oid") Long orderId,
                                                      @PathVariable("iid") Long orderItemId) {
        return ResponseEntity.ok(orderService.findOrderItemInOrderById(orderId, orderItemId));
    }

    @PostMapping("/{id}/items")
    public void addOrderItem(@PathVariable("id") Long id, @Valid @RequestBody OrderItemDto orderItemDto) {
        orderService.addOrderItem(id, orderItemDto);
    }

    @DeleteMapping("/{oid}/items/{iid}")
    public void deleteOrderItem(@PathVariable("oid") Long orderId,
                                @PathVariable("iid") Long orderItemId) {
        orderService.deleteOrderItem(orderId, orderItemId);
    }
}
