package com.epam.clothshop.service;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;

import java.util.List;

public interface CategoryService {
    Long createCategory(CategoryDto categoryDto);

    List<Category> getCategories();

    Category getCategoryById(long id);
}
