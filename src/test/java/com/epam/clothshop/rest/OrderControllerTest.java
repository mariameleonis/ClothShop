package com.epam.clothshop.rest;

import com.epam.clothshop.dto.OrderItemDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.OrderItem;
import com.epam.clothshop.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    @Test
    public void testGetAllOrders_WhenEverythingIsOk() throws Exception {

        when(orderService.getOrders()).thenReturn(ORDER_LIST);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(ORDER_LIST.size())))
                .andExpect(jsonPath("$[0].orderId", is(ORDER_1.getOrderId().intValue())))
                .andExpect(jsonPath("$[1].orderId", is(ORDER_2.getOrderId().intValue())))
                .andExpect(jsonPath("$[2].orderId", is(ORDER_3.getOrderId().intValue())));
    }

    @Test
    public void testGetOrderById_WhenEverythingIsOk() throws Exception {

        when(orderService.getOrderById(ORDER_1.getOrderId())).thenReturn(ORDER_1);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.orderId", is(ORDER_1.getOrderId().intValue())))
                .andExpect(jsonPath("$.totalPrice", is(ORDER_1.getTotalPrice().doubleValue())));
    }

    @Test
    public void testGetOrderById_WhenNotFound() throws Exception {

        when(orderService.getOrderById(42L)).thenThrow(new ResourceNotFoundException("Order with id: '42' not found"));

        mockMvc.perform(get("/api/orders/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrder_WhenEverythingIsOk() throws Exception {

        Long orderId = ORDER_1.getOrderId();

        mockMvc.perform(delete("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOrder_WhenNotFound() throws Exception {

        doThrow(new ResourceNotFoundException("Order with id: '42' not found")).when(orderService).deleteOrderById(42L);

        mockMvc.perform(delete("/api/orders/42")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrder_WhenInvalidArgumentSupplied() throws Exception {

        mockMvc.perform(delete("/api/orders/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCancelOrder_WhenEverythingIsOk() throws Exception {

        mockMvc.perform(post("/api/orders/1/cancel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testCancelOrder_WhenNotFound() throws Exception {

        Long id = (long) Math.random();

        doThrow(new ResourceNotFoundException()).when(orderService).cancelOrderById(anyLong());

        mockMvc.perform(post("/api/orders/{id}/cancel", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCancelOrder_WhenInvalidArgumentSupplied() throws Exception {

        mockMvc.perform(post("/api/orders/abc/cancel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllOrderItems_WhenEverythingIsOk() throws Exception {

        Set<OrderItem> orderItems = ORDER_1.getOrderItems();

        when(orderService.getOrderById(anyLong())).thenReturn(ORDER_1);

        mockMvc.perform(get("/api/orders/{id}/items", ORDER_1.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orderItems.size())))
                .andExpect(jsonPath("$[0].productId", is(ORDER_ITEM_1.getProductId().intValue())));
    }

    @Test
    public void testGetAllOrderItems_WhenNotFound() throws Exception {

        Long id = (long) Math.random();

        doThrow(new ResourceNotFoundException()).when(orderService).getOrderById(id);

        mockMvc.perform(get("/api/orders/{id}/items", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllOrderItems_WhenInvalidArgumentSupplied() throws Exception {

        String invalidArg = "abc";

        mockMvc.perform(get("/api/orders/{id}/items", invalidArg)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddOrderItem_WhenEverythingIsOk() throws Exception {

        mockMvc.perform(post("/api/orders/{id}/items", ORDER_1.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_ORDER_ITEM_DTO)))
                .andExpect(status().isOk());

    }

    @Test
    public void testAddOrderItem_WhenNotFound() throws Exception {

        Long id = (long) Math.random();

        doThrow(new ResourceNotFoundException()).when(orderService).addOrderItem(anyLong(), any(OrderItemDto.class));

        mockMvc.perform(post("/api/orders/{id}/items", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_ORDER_ITEM_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddOrderItem_WhenInvalidOrderItemSupplied() throws Exception {

        mockMvc.perform(post("/api/orders/{id}/items", ORDER_1.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_ORDER_ITEM_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetOrderItemById_WhenEverythingIsOk() throws Exception {

        when(orderService.findOrderItemInOrderById(anyLong(), anyLong())).thenReturn(ORDER_ITEM_1);

        mockMvc.perform(get("/api/orders/{oid}/items/{iid}", ORDER_1.getOrderId(), ORDER_ITEM_1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ORDER_ITEM_1.getId().intValue())));

    }

    @Test
    public void testGetOrderItemById_WhenNotFound() throws Exception {

        Long orderId = (long) Math.random();
        Long orderItemId = (long) Math.random();

        when(orderService.findOrderItemInOrderById(anyLong(), anyLong())).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/api/orders/{oid}/items/{iid}", orderId, orderItemId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetOrderItemById_WhenInvalidArgumentSupplied() throws Exception {

        String orderId = "xyz";
        String orderItemId = "klm";

        mockMvc.perform(get("/api/orders/{oid}/items/{iid}", orderId, orderItemId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteOrderItem_WhenEverythingIsOk() throws Exception {

        mockMvc.perform(delete("/api/orders/{oid}/items/{iid}", ORDER_1.getOrderId(), ORDER_ITEM_1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOrderItem_WhenNotFound() throws Exception {

        Long orderId = (long) Math.random();
        Long orderItemId = (long) Math.random();

        doThrow(new ResourceNotFoundException()).when(orderService).deleteOrderItem(anyLong(), anyLong());

        mockMvc.perform(delete("/api/orders/{oid}/items/{iid}", orderId, orderItemId))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testDeleteOrderItem_WhenInvalidArgumentSupplied() throws Exception {

        String orderId = "xyz";
        String orderItemId = "klm";

        mockMvc.perform(delete("/api/orders/{oid}/items/{iid}", orderId, orderItemId))
                .andExpect(status().isBadRequest());
    }
}
