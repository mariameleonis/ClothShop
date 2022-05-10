package com.epam.clothshop.service;

import com.epam.clothshop.dao.CategoryRepository;
import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.lang.model.util.Types;

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

    @Captor
    private ArgumentCaptor<Category> argumentCaptor;

    @Test
    public void testSaveCategory() {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(1L);
        categoryDto.setCategoryName("Dresses");

        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Dresses");

        when(categoryRepository.save(argumentCaptor.capture())).thenReturn(category);

        Long resultCategoryId = categoryService.createCategory(categoryDto);
        assertThat(resultCategoryId, is(1L));

        assertThat(argumentCaptor.getValue().getCategoryName(), is("Dresses"));

    }
}
