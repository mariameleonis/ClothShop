package com.epam.clothshop.rest;

import com.epam.clothshop.dto.OrderDto;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.epam.clothshop.ClothShopTestData.*;
import static com.epam.clothshop.ClothShopTestData.PRODUCT_3;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    @Autowired
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<OrderDto> argumentCaptor;

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
}
