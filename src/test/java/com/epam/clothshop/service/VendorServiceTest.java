package com.epam.clothshop.service;

import com.epam.clothshop.dao.VendorRepository;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Vendor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorService vendorService;

    @Captor
    private ArgumentCaptor<Vendor> argumentCaptor;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testSaveVendor() {

        VendorDto vendorDto = new VendorDto(VENDOR_2.getVendorName());

        when(vendorRepository.save(argumentCaptor.capture())).thenReturn(VENDOR_2);

        Long resultVendorId = vendorService.createVendor(vendorDto);

        assertThat(resultVendorId, is(VENDOR_2.getVendorId()));
        assertThat(argumentCaptor.getValue().getVendorName(), is(VENDOR_2.getVendorName()));
    }

    @Test
    public void testGetAllVendors() {

        List<Vendor> vendors = new ArrayList<>(List.of(VENDOR_1, VENDOR_2, VENDOR_3));

        when(vendorRepository.findAll()).thenReturn(vendors);

        List<Vendor> resultVendors = vendorService.getVendors();

        assertThat(resultVendors.size(), is(vendors.size()));
        assertThat(resultVendors.get(0).getVendorId(), is(VENDOR_1.getVendorId()));
        assertThat(resultVendors.get(0).getVendorName(), is(VENDOR_1.getVendorName()));
    }

    @Test
    public void testGetVendorById() {

        when(vendorRepository.findById(VENDOR_2.getVendorId())).thenReturn(Optional.of(VENDOR_2));

        Vendor resultVendor = vendorService.getVendorById(VENDOR_2.getVendorId());

        assertThat(resultVendor.getVendorId(), is(VENDOR_2.getVendorId()));
        assertThat(resultVendor.getVendorName(), is(VENDOR_2.getVendorName()));
    }

    @Test
    public void updateVendorTest() {

        VendorDto vendorDto = modelMapper.map(VENDOR_2_UPDATE, VendorDto.class);

        when(vendorRepository.findById(VENDOR_2.getVendorId())).thenReturn(Optional.of(VENDOR_2));

        Vendor vendor = vendorService.updateVendor(VENDOR_2.getVendorId(), vendorDto);

        assertThat(vendor.getVendorName(), is(VENDOR_2_UPDATE.getVendorName()));
    }

    @Test
    public void deleteVendorByIdTest() {

       doNothing().when(vendorRepository).deleteById(VENDOR_3.getVendorId());

       vendorService.deleteVendorById(VENDOR_3.getVendorId());

       verify(vendorRepository, times(1)).deleteById(VENDOR_3.getVendorId());
    }
}
