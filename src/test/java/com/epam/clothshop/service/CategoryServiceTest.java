package com.epam.clothshop.service;

import com.epam.clothshop.dao.CategoryRepository;
import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testSaveCategory() {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Dresses");

        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Dresses");

        when(categoryRepository.save(category)).thenReturn(category);

        Long resultCategoryId = categoryService.createCategory(categoryDto);
        assertThat(resultCategoryId, is(1));

        verify(categoryRepository).save(category);

    }
}
