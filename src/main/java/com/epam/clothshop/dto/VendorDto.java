package com.epam.clothshop.dto;

import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class VendorDto extends Dto {

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Long vendorId;

    @NotEmpty(groups = {New.class, Update.class})
    @Size(min = 2, max = 25, groups = {New.class, Update.class})
    private String vendorName;

    @Tolerate
    public VendorDto(Long vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }
}
