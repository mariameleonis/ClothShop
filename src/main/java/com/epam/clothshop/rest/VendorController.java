package com.epam.clothshop.rest;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping
    public ResponseEntity<Void> createVendor(@Validated(VendorDto.New.class) @RequestBody VendorDto vendorDto,
                                             UriComponentsBuilder uriComponentsBuilder) {

        Long id = vendorService.createVendor(vendorDto);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/vendors/{id}").buildAndExpand(id);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(header, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getVendors() {
        return ResponseEntity.ok(vendorService.getVendors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Long id) {

        Optional<Vendor> vendor = vendorService.getVendorById(id);

        if (vendor.isEmpty()) {
            throw new NotFound404Exception(String.format("Vendor with id: '%s' not found", id));
        }
        return ResponseEntity.ok(vendor.get());
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByVendor(@PathVariable("id") Long id) {

        Optional<Vendor> vendor = vendorService.getVendorById(id);

        if (vendor.isEmpty()) {
            throw new NotFound404Exception(String.format("Vendor with id: '%s' not found", id));
        }

        return ResponseEntity.ok(vendor.get().getProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable("id") Long id, @Validated(VendorDto.Update.class) @RequestBody VendorDto vendorDto) {

        Optional<Vendor> vendor = vendorService.getVendorById(id);

        if (vendor.isEmpty()) {
            throw new NotFound404Exception(String.format("Vendor with id: '%s' not found", id));
        }

        return ResponseEntity.ok(vendorService.updateVendor(id, vendorDto));
    }

    @DeleteMapping("/{id}")
    public void deleteVendorById(@PathVariable("id") Long id) {

        Optional<Vendor> vendor = vendorService.getVendorById(id);

        if (vendor.isEmpty()) {
            throw new NotFound404Exception(String.format("Vendor with id: '%s' not found", id));
        }

        vendorService.deleteVendorById(id);
    }

    @PostMapping("/{id}/products")
    public void addProductToVendor(@PathVariable("id") Long id, @Validated(ProductDto.AddToVendor.class) @RequestBody ProductDto productDto) {

        Optional<Vendor> vendor = vendorService.getVendorById(id);

        if (vendor.isEmpty()) {
            throw new NotFound404Exception(String.format("Vendor with id: '%s' not found", id));
        }

        vendorService.addProductToVendor(id, productDto);
    }
}
