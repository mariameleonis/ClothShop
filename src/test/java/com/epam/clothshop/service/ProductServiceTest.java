package com.epam.clothshop.service;

import com.epam.clothshop.dao.ProductRepository;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void testAddOrUpdatePhoto_WhenEverythingIsOk() throws IOException {

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(PRODUCT_1));

        File file = new File("src/test/java/com/epam/clothshop/service/img/photo.jpeg");

        byte[] photo = Files.readAllBytes(file.toPath());

        productService.addOrUpdatePhoto(PRODUCT_1.getId(), photo);

        assertTrue(PRODUCT_1.getPhoto().equals(photo));
    }

    @Test
    public void testAddOrUpdatePhoto_WhenProductNotFound() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.addOrUpdatePhoto(PRODUCT_1.getId(), Files.readAllBytes(new File("src/test/java/com/epam/clothshop/service/img/photo.jpeg").toPath()));
        });
    }

    @Test
    public void testGetPhoto_WhenEverythingIsOk() throws IOException {

        byte[] expectedPhoto = Files.readAllBytes(new File("src/test/java/com/epam/clothshop/service/img/photo.jpeg").toPath());

        PRODUCT_2.setPhoto(expectedPhoto);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(PRODUCT_2));

        byte[] actualPhoto = productService.getPhoto(PRODUCT_2.getId());

        assertTrue(actualPhoto.equals(expectedPhoto));
    }

    @Test
    public void testGetPhoto_WhenProductNotFound() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getPhoto(PRODUCT_2.getId());
        });
    }
}
