package com.epam.clothshop.rest;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

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
       return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id).getProducts());
    }
}
