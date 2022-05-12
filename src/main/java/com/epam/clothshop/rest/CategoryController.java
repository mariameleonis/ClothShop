package com.epam.clothshop.rest;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@Validated(CategoryDto.New.class) @RequestBody CategoryDto categoryDto,
                                               UriComponentsBuilder uriComponentsBuilder) {

        Long id = categoryService.createCategory(categoryDto);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/categories/{id}").buildAndExpand(id);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(header, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {

       Optional<Category> category = categoryService.getCategoryById(id);

       if (category.isEmpty()) {
           throw new NotFound404Exception(String.format("Category with id: '%s' not found"));
       }
       return ResponseEntity.ok(category.get());
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Set<Product>> getProductsByCategory(@PathVariable("id") Long id) {

        Optional<Category> category = categoryService.getCategoryById(id);

        if (category.isEmpty()) {
            throw new NotFound404Exception(String.format("Category with id: '%s' not found"));
        }

        return ResponseEntity.ok(category.get().getProducts());
    }
}
