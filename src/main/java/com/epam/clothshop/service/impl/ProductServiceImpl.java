package com.epam.clothshop.service.impl;

import com.epam.clothshop.dao.ProductRepository;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id: '%s' not found", id)));
    }

    @Override
    public Long createProduct(ProductDto productDto) {
        return productRepository.save(convertDtoToEntity(productDto)).getId();
    }

    private Product convertDtoToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        getProductById(productDto.getId());
        return productRepository.save(convertDtoToEntity(productDto));
    }

    @Override
    public void deleteProductById(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }

    @Override
    public void addOrUpdatePhoto(Long id, byte[] photo) {

    }

    @Override
    public byte[] getPhoto(Long id) {
        return new byte[0];
    }
}
