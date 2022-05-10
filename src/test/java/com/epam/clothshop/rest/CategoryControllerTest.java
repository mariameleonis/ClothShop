package com.epam.clothshop.rest;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.service.CategoryService;
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

import java.util.List;

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
                .andExpect(jsonPath("$[0].categoryId", is(1L)))
                .andExpect(jsonPath("$[1].categoryName", is("Skirts")))
                .andExpect(jsonPath("$[1].categoryId", is(2L)))
                .andExpect(jsonPath("$[2].categoryName", is("Tops")))
                .andExpect(jsonPath("$[2].categoryId", is(3L)));
    }

    private Category createCategory(long id, String name) {

        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(name);

        return  category;
    }
}
