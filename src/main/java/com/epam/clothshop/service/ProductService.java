package com.epam.clothshop.service;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.model.Product;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
@Service
public interface ProductService {
    List<Product> getProducts();
    Product getProductById(Long id);
    Long createProduct(ProductDto productDto);
    Product updateProduct(ProductDto productDto);
    void deleteProductById(Long id);
    void addOrUpdatePhoto(Long id, byte[] photo);
    byte[] getPhoto(Long id);
}
