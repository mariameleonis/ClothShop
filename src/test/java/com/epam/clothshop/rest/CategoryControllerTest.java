package com.epam.clothshop.rest;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    @Captor
    private ArgumentCaptor<CategoryDto> argumentCaptor;

    @Test
    public void testCreateCategory_WhenEverythingIsOk() throws Exception {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Dresses");

        when(categoryService.createCategory(argumentCaptor.capture())).thenReturn(1L);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/categories/1"));

        assertThat(argumentCaptor.getValue().getCategoryName(), is("Dresses"));
    }

    @Test
    public void testCreateCategory_WhenInvalidCategorySupplied() throws Exception {

        CategoryDto categoryDto = new CategoryDto();

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllCategories_WhenEverythingIsOk() throws Exception {

        when(categoryService.getCategories()).thenReturn(List.of(createCategory(1L, "Dresses"),
                createCategory(2L, "Skirts"), createCategory(3L, "Tops")));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].categoryName", is("Dresses")))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[1].categoryName", is("Skirts")))
                .andExpect(jsonPath("$[1].categoryId", is(2)))
                .andExpect(jsonPath("$[2].categoryName", is("Tops")))
                .andExpect(jsonPath("$[2].categoryId", is(3)));
    }

    @Test
    public void testGetCategoryById_WhenEverythingIsOk() throws Exception {

        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(createCategory(1L, "Dresses")));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.categoryName", is("Dresses")))
                .andExpect(jsonPath("$.categoryId", is(1)));
    }

    @Test
    public void testGetCategoryById_WhenNotFound() throws Exception {

        when(categoryService.getCategoryById(42L)).thenThrow(new NotFound404Exception("Category with id: '42' not found"));

        mockMvc.perform(get("/api/categories/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductsByCategory_WhenEverythingIsOk() throws Exception {

        Category category = createCategory(1L, "Dresses");
        category.setProducts(Set.of(createProduct(1L, "Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L),
                createProduct(2L, "Rose Cocktail Dress", BigDecimal.valueOf(110.70), 3, 1L, 2L),
                createProduct(3L, "Night Blue Dress", BigDecimal.valueOf(98.40), 7, 1L, 3L)));

        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/api/categories/1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Little Black Dress")))
                .andExpect(jsonPath("$[0].price", is(120.50)))
                .andExpect(jsonPath("$[0].unitsInStock", is(15)))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[0].vendorId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Rose Cocktail Dress")))
                .andExpect(jsonPath("$[1].price", is(110.70)))
                .andExpect(jsonPath("$[1].unitsInStock", is(3)))
                .andExpect(jsonPath("$[1].categoryId", is(1)))
                .andExpect(jsonPath("$[1].vendorId", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Night Blue Dress")))
                .andExpect(jsonPath("$[2].price", is(98.40)))
                .andExpect(jsonPath("$[2].unitsInStock", is(7)))
                .andExpect(jsonPath("$[2].categoryId", is(1)))
                .andExpect(jsonPath("$[2].vendorId", is(3)));
    }

    private Product createProduct(long id, String name, BigDecimal price, int quantity, long categoryId, long vendorId) {

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setUnitsInStock(quantity);
        product.setCategoryId(categoryId);
        product.setVendorId(vendorId);

        return product;
    }

    private Category createCategory(long id, String name) {

        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(name);

        return  category;
    }
}
