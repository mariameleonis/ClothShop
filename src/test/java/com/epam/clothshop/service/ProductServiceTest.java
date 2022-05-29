package com.epam.clothshop.service;

import com.epam.clothshop.dao.ProductRepository;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> argumentCaptor;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(PRODUCT_LIST);

        List<Product> resultProducts = productService.getProducts();

        assertEquals(resultProducts, PRODUCT_LIST);
    }

    @Test
    public void testGetProductById() {

        when(productRepository.findById(PRODUCT_1.getId())).thenReturn(Optional.of(PRODUCT_1));

        Product resultProduct = productService.getProductById(PRODUCT_1.getId());

        assertEquals(resultProduct, PRODUCT_1);
    }

    @Test
    public void testCreateProduct() {

        when(productMapper.map(VALID_PRODUCT_DTO_ADD_TO_VENDOR, Product.class)).thenReturn(PRODUCT_1);

        when(productRepository.save(argumentCaptor.capture())).thenReturn(PRODUCT_1);

        Long resultProductId = productService.createProduct(VALID_PRODUCT_DTO_ADD_TO_VENDOR);

        assertThat(resultProductId, is(PRODUCT_1.getId()));
        assertThat(argumentCaptor.getValue().getName(), is(PRODUCT_1.getName()));
    }

    @Test
    public void testUpdateProduct() {

        ProductDto productDto = modelMapper.map(PRODUCT_1_UPDATE, ProductDto.class);

        when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(PRODUCT_1));

        when(productMapper.map(productDto, Product.class)).thenReturn(PRODUCT_1_UPDATE);

        when(productRepository.save(PRODUCT_1_UPDATE)).thenReturn(PRODUCT_1_UPDATE);

        Product product = productService.updateProduct(productDto);

        assertEquals(product, PRODUCT_1_UPDATE);
    }

    @Test
    public void testDeleteProductById() {

        when(productRepository.findById(PRODUCT_3.getId())).thenReturn(Optional.of(PRODUCT_3));

        doNothing().when(productRepository).deleteById(PRODUCT_3.getId());

        productService.deleteProductById(PRODUCT_3.getId());

        verify(productRepository, times(1)).deleteById(PRODUCT_3.getId());
    }
}
