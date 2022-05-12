package com.epam.clothshop.rest;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.CategoryService;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.service.VendorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VendorService vendorService;

    @Captor
    private ArgumentCaptor<VendorDto> argumentCaptor;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testCreateVendor_WhenEverythingIsOk() throws Exception {

        VendorDto vendorDto = new VendorDto();
        vendorDto.setVendorName("H&M");

        when(vendorService.createVendor(argumentCaptor.capture())).thenReturn(1L);

        mockMvc.perform(post("/api/vendors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendorDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/vendors/1"));

        assertThat(argumentCaptor.getValue().getVendorName(), is("H&M"));
    }

    @Test
    public void testCreateVendor_WhenInvalidVendorSupplied() throws Exception {

        VendorDto vendorDto = new VendorDto();

        mockMvc.perform(post("/api/vendors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendorDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllVendors_WhenEverythingIsOk() throws Exception {

        when(vendorService.getVendors()).thenReturn(List.of(new Vendor(1L, "H&M"),
                new Vendor(2L, "Pull&Bear"), new Vendor(3L, "Tops")));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].categoryName", is("Dresses")))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[1].categoryName", is("Skirts")))
                .andExpect(jsonPath("$[1].categoryId", is(2)))
                .andExpect(jsonPath("$[2].categoryName", is("Tops")))
                .andExpect(jsonPath("$[2].categoryId", is(3)));
    }

    @Test
    public void testGetVendorById_WhenEverythingIsOk() throws Exception {

        when(vendorService.getVendorById(1L)).thenReturn(Optional.of(new Vendor(1L, "H&M")));

        mockMvc.perform(get("/api/vendors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.vendorName", is("H&M")))
                .andExpect(jsonPath("$.vendorId", is(1)));
    }

    @Test
    public void testGetVendorById_WhenNotFound() throws Exception {

        when(vendorService.getVendorById(42L)).thenThrow(new NotFound404Exception("Vendor with id: '42' not found"));

        mockMvc.perform(get("/api/vendors/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductsByVendor_WhenEverythingIsOk() throws Exception {

        Vendor vendor = new Vendor(1L, "H&M", Set.of(new Product(1L, "Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L),
                new Product(2L, "Rose Cocktail Dress", BigDecimal.valueOf(110.70), 3, 1L, 1L),
                new Product(3L, "Night Blue Dress", BigDecimal.valueOf(98.40), 7, 1L, 1L)));

        when(vendorService.getVendorById(1L)).thenReturn(Optional.of(vendor));

        mockMvc.perform(get("/api/vendors/1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Little Black Dress")))
                .andExpect(jsonPath("$[0].price", is(120.50)))
                .andExpect(jsonPath("$[0].unitsInStock", is(15)))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[0].vendorId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Rose Cocktail Dress")))
                .andExpect(jsonPath("$[1].price", is(110.70)))
                .andExpect(jsonPath("$[1].unitsInStock", is(3)))
                .andExpect(jsonPath("$[1].categoryId", is(1)))
                .andExpect(jsonPath("$[1].vendorId", is(1)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Night Blue Dress")))
                .andExpect(jsonPath("$[2].price", is(98.40)))
                .andExpect(jsonPath("$[2].unitsInStock", is(7)))
                .andExpect(jsonPath("$[2].categoryId", is(1)))
                .andExpect(jsonPath("$[2].vendorId", is(1)));
    }

    @Test
    public void testUpdateVendor_WhenEverythingIsOk() throws Exception {

        VendorDto vendorDto = new VendorDto();
        vendorDto.setVendorName("Zara");

        when(vendorService.updateVendor(eq(1L), argumentCaptor.capture())).thenReturn(new Vendor(1L, "Zara"));

        mockMvc.perform(put("/api/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendorDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.vendorName", is("Zara")))
                .andExpect(jsonPath("$.vendorId", is(1)));

        assertThat(argumentCaptor.getValue().getVendorName(), is("Zara"));
    }


    @Test
    public void testUpdateVendorWithUnknownId_WhenReturn404() throws Exception {

        VendorDto vendorDto = new VendorDto();
        vendorDto.setVendorName("Zara");

        when(vendorService.updateVendor(eq(42L), argumentCaptor.capture())).thenThrow(new NotFound404Exception("Vendor with id: '42' not found"));

        mockMvc.perform(put("/api/vendors/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendorDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddProductToVendor_WhenEverythingIsOk() throws Exception {

        Vendor vendor = new Vendor(1L, "Zara");

        ProductDto productDto = new ProductDto();
        productDto.setName("Little Black Dress");
        productDto.setPrice(BigDecimal.valueOf(120.80));
        productDto.setUnitsInStock(15);
        productDto.setCategoryId(1L);

        Product productToAdd = modelMapper.map(productDto, Product.class);

        Set<Product> products = new HashSet<>();
        products.add(productToAdd);

        vendor.setProducts(products);

        when(vendorService.addProductToVendor(1L, productDto)).thenReturn(vendor);

        mockMvc.perform(post("/api/vendors/1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.vendorName", is("Zara")))
                .andExpect(jsonPath("$.vendorId", is(1)))
                .andExpect(jsonPath("$.products", contains(productToAdd)));
    }

    @Test
    public void testAddProductToVendor_WhenInvalidProductSupplied() throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setName("");

        mockMvc.perform(post("/api/vendors/1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddProductToVendor_WhenVendorNotFound() throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setName("Little Black Dress");
        productDto.setPrice(BigDecimal.valueOf(120.80));
        productDto.setUnitsInStock(15);
        productDto.setCategoryId(1L);

        when(vendorService.addProductToVendor(42L, productDto)).thenThrow(new NotFound404Exception("Vendor with id: '42' not found"));

        mockMvc.perform(post("/api/vendors/42/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound());
    }
}
