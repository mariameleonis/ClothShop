package com.epam.clothshop.service;

import com.epam.clothshop.dao.CategoryRepository;
import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long createCategory(CategoryDto categoryDto) {

        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());

        category = categoryRepository.save(category);

        return category.getCategoryId();
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(long id) {
        return categoryRepository.findById(id);
    }
}
