package com.epam.clothshop.service;

import com.epam.clothshop.dao.VendorRepository;
import com.epam.clothshop.dto.CategoryDto;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Vendor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Long createVendor(VendorDto vendorDto) {

        Vendor vendor = new Vendor();

        vendor.setVendorName(vendorDto.getVendorName());

        vendor = vendorRepository.save(vendor);

        return vendor.getVendorId();
    }

    public List<Vendor> getVendors() {
        return null;
    }

    public Vendor getVendorById(long id) {
        return null;
    }

    public Vendor updateVendor(long id, VendorDto vendorDto) {
        return null;
    }

    public Vendor addProductToVendor(long id, ProductDto productDto) {
        return null;
    }

    public Vendor deleteVendorById(Long id) {
        return null;
    }
}
