package com.epam.clothshop.rest;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.VendorService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    final Product PRODUCT_1 = new Product(1L, "Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L);
    final Product PRODUCT_2 = new Product(2L, "Floral Cocktail Dress", BigDecimal.valueOf(110.70), 10, 1L, 1L);
    final Product PRODUCT_3 = new Product(3L, "Blue Polka-Dot Dress", BigDecimal.valueOf(100.90), 19, 1L, 1L);

    final Vendor VENDOR_1 = new Vendor(1L, "H&M", List.of(PRODUCT_1, PRODUCT_2, PRODUCT_3));
    final Vendor VENDOR_2 = new Vendor(2L, "Zara");

    final Vendor VENDOR_2_UPDATE = new Vendor(2L, "Mango");
    final Vendor VENDOR_3 = new Vendor(3L, "Bershka");

    final ProductDto VALID_PRODUCT_DTO = new ProductDto("Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L);

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

        List<Vendor> vendors = new ArrayList<>(Arrays.asList(VENDOR_1, VENDOR_2, VENDOR_3));

        when(vendorService.getVendors()).thenReturn(vendors);

        mockMvc.perform(get("/api/vendors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(vendors.size())))
                .andExpect(jsonPath("$[0].vendorName", is(VENDOR_1.getVendorName())))
                .andExpect(jsonPath("$[0].vendorId", is(VENDOR_1.getVendorId().intValue())))
                .andExpect(jsonPath("$[1].vendorName", is(VENDOR_2.getVendorName())))
                .andExpect(jsonPath("$[1].vendorId", is(VENDOR_2.getVendorId().intValue())))
                .andExpect(jsonPath("$[2].vendorName", is(VENDOR_3.getVendorName())))
                .andExpect(jsonPath("$[2].vendorId", is(VENDOR_3.getVendorId().intValue())));
    }

    @Test
    public void testGetVendorById_WhenEverythingIsOk() throws Exception {

        when(vendorService.getVendorById(VENDOR_1.getVendorId())).thenReturn(VENDOR_1);

        mockMvc.perform(get("/api/vendors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.vendorName", is(VENDOR_1.getVendorName())))
                .andExpect(jsonPath("$.vendorId", is(VENDOR_1.getVendorId().intValue())));
    }

    @Test
    public void testGetVendorById_WhenNotFound() throws Exception {

        when(vendorService.getVendorById(42L)).thenThrow(new ResourceNotFoundException("Vendor with id: '42' not found"));

        mockMvc.perform(get("/api/vendors/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductsByVendor_WhenEverythingIsOk() throws Exception {

        when(vendorService.getVendorById(VENDOR_1.getVendorId())).thenReturn(VENDOR_1);

        List<Product> products = VENDOR_1.getProducts();

        mockMvc.perform(get("/api/vendors/1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(products.size())))
                .andExpect(jsonPath("$[0].id", is(products.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(products.get(0).getName())))
                .andExpect(jsonPath("$[0].price", is(products.get(0).getPrice().doubleValue())))
                .andExpect(jsonPath("$[0].unitsInStock", is(products.get(0).getUnitsInStock())))
                .andExpect(jsonPath("$[0].categoryId", is(products.get(0).getCategoryId().intValue())))
                .andExpect(jsonPath("$[0].vendorId", is(products.get(0).getVendorId().intValue())))
                .andExpect(jsonPath("$[1].id", is(products.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(products.get(1).getName())))
                .andExpect(jsonPath("$[1].price", is(products.get(1).getPrice().doubleValue())))
                .andExpect(jsonPath("$[1].unitsInStock", is(products.get(1).getUnitsInStock())))
                .andExpect(jsonPath("$[1].categoryId", is(products.get(1).getCategoryId().intValue())))
                .andExpect(jsonPath("$[1].vendorId", is(products.get(1).getVendorId().intValue())))
                .andExpect(jsonPath("$[2].id", is(products.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is(products.get(2).getName())))
                .andExpect(jsonPath("$[2].price", is(products.get(2).getPrice().doubleValue())))
                .andExpect(jsonPath("$[2].unitsInStock", is(products.get(2).getUnitsInStock())))
                .andExpect(jsonPath("$[2].categoryId", is(products.get(2).getCategoryId().intValue())))
                .andExpect(jsonPath("$[2].vendorId", is(products.get(2).getVendorId().intValue())));
    }

    @Test
    public void testUpdateVendor_WhenEverythingIsOk() throws Exception {

        VendorDto vendorDto = modelMapper.map(VENDOR_2_UPDATE, VendorDto.class);

        when(vendorService.getVendorById(VENDOR_2.getVendorId())).thenReturn(VENDOR_2);
        when(vendorService.updateVendor(eq(VENDOR_2.getVendorId()), argumentCaptor.capture())).thenReturn(VENDOR_2_UPDATE);

        mockMvc.perform(put("/api/vendors/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendorDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.vendorName", is(VENDOR_2_UPDATE.getVendorName())))
                .andExpect(jsonPath("$.vendorId", is(VENDOR_2_UPDATE.getVendorId().intValue())));

        assertThat(argumentCaptor.getValue().getVendorName(), is(vendorDto.getVendorName()));
    }


    @Test
    public void testUpdateVendorWithUnknownId_WhenReturn404() throws Exception {

        VendorDto vendorDto = new VendorDto();
        vendorDto.setVendorId(42L);
        vendorDto.setVendorName("Zara");

        when(vendorService.updateVendor(eq(42L), argumentCaptor.capture())).thenThrow(new ResourceNotFoundException("Vendor with id: '42' not found"));

        mockMvc.perform(put("/api/vendors/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendorDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteVendor_WhenEverythingIsOk() throws Exception {

        when(vendorService.getVendorById(VENDOR_1.getVendorId())).thenReturn(VENDOR_1);

        mockMvc.perform(delete("/api/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVendor_WhenNotFound() throws Exception {

        when(vendorService.deleteVendorById(VENDOR_1.getVendorId())).thenThrow(new ResourceNotFoundException("Vendor with id: '42' not found"));

        mockMvc.perform(delete("/api/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteVendor_WhenInvalidArgumentSupplied() throws Exception {

        mockMvc.perform(delete("/api/vendors/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddProductToVendor_WhenEverythingIsOk() throws Exception {

        when(vendorService.getVendorById(VENDOR_1.getVendorId())).thenReturn(VENDOR_1);

        mockMvc.perform(post("/api/vendors/1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_PRODUCT_DTO)))
                .andExpect(status().isOk());
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

        when(vendorService.addProductToVendor(42L, VALID_PRODUCT_DTO)).thenThrow(new ResourceNotFoundException("Vendor with id: '42' not found"));

        mockMvc.perform(post("/api/vendors/42/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_PRODUCT_DTO)))
                .andExpect(status().isNotFound());
    }
}
