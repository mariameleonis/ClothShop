package com.epam.clothshop.service;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Vendor;

import javax.transaction.Transactional;
import java.util.List;

public interface VendorService {
    Long createVendor(VendorDto vendorDto);

    List<Vendor> getVendors();

    Vendor getVendorById(long id);

    @Transactional
    Vendor updateVendor(long id, VendorDto vendorDto);

    @Transactional
    Vendor addProductToVendor(long id, ProductDto productDto);

    Vendor deleteVendorById(Long id);
}
