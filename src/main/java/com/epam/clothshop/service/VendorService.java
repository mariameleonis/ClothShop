package com.epam.clothshop.service;

import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Vendor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VendorService {
    public Long createVendor(VendorDto vendorDto) {
        return null;
    }

    public List<Vendor> getVendors() {
        return null;
    }

    public Optional<Vendor> getVendorById(long id) {
        return null;
    }

    public Vendor updateVendor(long id, VendorDto vendorDto) {
        return null;
    }

    public Vendor addProductToVendor(long id, ProductDto productDto) {
        return null;
    }

    public void deleteVendorById(Long id) {
    }
}
