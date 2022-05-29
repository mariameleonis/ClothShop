package com.epam.clothshop.rest;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<ProductDto> argumentCaptor;

    @Test
    public void testCreateProduct_WhenEverythingIsOk() throws Exception {
        when(productService.createProduct(argumentCaptor.capture())).thenReturn(PRODUCT_1.getId());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_PRODUCT_DTO)))
                .andExpect(status().isCreated());

        assertThat(argumentCaptor.getValue().getName(), is(VALID_PRODUCT_DTO_ADD_TO_VENDOR.getName()));
    }

    @Test
    public void testCreateProduct_WhenInvalidProductSupplied() throws Exception {

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_PRODUCT_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllProducts_WhenEverythingIsOk() throws Exception {

        when(productService.getProducts()).thenReturn(PRODUCT_LIST);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(PRODUCT_LIST.size())))
                .andExpect(jsonPath("$[0].id", is(PRODUCT_1.getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(PRODUCT_2.getId().intValue())))
                .andExpect(jsonPath("$[2].id", is(PRODUCT_3.getId().intValue())));
    }

    @Test
    public void testGetProductById_WhenEverythingIsOk() throws Exception {

        when(productService.getProductById(PRODUCT_1.getId())).thenReturn(PRODUCT_1);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(PRODUCT_1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(PRODUCT_1.getName())));
    }

    @Test
    public void testGetProductById_WhenNotFound() throws Exception {

        when(productService.getProductById(42L)).thenThrow(new ResourceNotFoundException("Product with id: '42' not found"));

        mockMvc.perform(get("/api/products/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct_WhenEverythingIsOk() throws Exception {
        ProductDto productDto = modelMapper.map(PRODUCT_1_UPDATE, ProductDto.class);

        when(productService.getProductById(PRODUCT_1.getId())).thenReturn(PRODUCT_1);
        when(productService.updateProduct(productDto)).thenReturn(PRODUCT_1_UPDATE);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.price", is(PRODUCT_1_UPDATE.getPrice().doubleValue())))
                .andExpect(jsonPath("$.name", is(PRODUCT_1_UPDATE.getName())));
    }

    @Test
    public void testUpdateProductWithUnknownId_WhenNotFound() throws Exception {
        when(productService.updateProduct(INVALID_PRODUCT_DTO_UPDATE)).thenThrow(new ResourceNotFoundException("Product with id: '42' not found"));

        mockMvc.perform(put("/api/products/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_PRODUCT_DTO_UPDATE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct_WhenInvalidUserSupplied() throws Exception {
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_PRODUCT_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteProduct_WhenEverythingIsOk() throws Exception {

        when(productService.getProductById(PRODUCT_1.getId())).thenReturn(PRODUCT_1);

        mockMvc.perform(delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProduct_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Product with id: '42' not found")).when(productService).deleteProductById(42L);
        mockMvc.perform(delete("/api/products/42")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProduct_WhenInvalidArgumentSupplied() throws Exception {

        mockMvc.perform(delete("/api/products/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
