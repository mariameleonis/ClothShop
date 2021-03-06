package com.epam.clothshop.service.impl;

import com.epam.clothshop.dao.VendorRepository;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class VendorServiceImpl implements com.epam.clothshop.service.VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Long createVendor(VendorDto vendorDto) {

        Vendor vendor = new Vendor();

        vendor.setVendorName(vendorDto.getVendorName());

        vendor = vendorRepository.save(vendor);

        return vendor.getVendorId();
    }

    @Override
    public List<Vendor> getVendors() {

        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(long id) {

        Optional<Vendor> requestedVendor = vendorRepository.findById(id);

        if (requestedVendor.isEmpty()) {
                throw new ResourceNotFoundException(String.format("Vendor with id: '%s' not found", id));
        }

        return requestedVendor.get();
    }

    @Override
    @Transactional
    public Vendor updateVendor(long id, VendorDto vendorDto) {

        Vendor vendor = getVendorById(id);
        vendor.setVendorName(vendorDto.getVendorName());

        return vendor;
    }

    @Override
    @Transactional
    public Vendor addProductToVendor(long id, ProductDto productDto) {

        Product productToAdd = mapper.map(productDto, Product.class);
        Vendor vendor = getVendorById(id);
        List<Product> products = vendor.getProducts();

        if(products == null) {
            products = new ArrayList<>();
        }

        products.add(productToAdd);

        vendor.setProducts(products);

        return vendor;
    }

    @Override
    public Vendor deleteVendorById(Long id) {

        Vendor vendor = getVendorById(id);
        vendorRepository.deleteById(id);

        return vendor;
    }
}
