package com.epam.clothshop.service;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.epam.clothshop.dao.VendorRepository;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.rest.VendorControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        VendorDto vendorDto = modelMapper.map(VENDOR_1, VendorDto.class);

        when(vendorRepository.save(argumentCaptor.capture())).thenReturn(VENDOR_1);

        Long resultVendorId = vendorService.createVendor(vendorDto);

        assertThat(resultVendorId, is(VENDOR_1.getVendorId()));

        assertThat(argumentCaptor.getValue().getVendorName(), is(VENDOR_1.getVendorName()));
    }
}
