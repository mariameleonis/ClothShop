package com.epam.clothshop.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class VendorDto {

    interface New {
    }

    interface Exist {
    }

    interface Update extends Exist {
    }

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Long vendorId;

    @NotEmpty(groups = {New.class, Update.class})
    @Size(min = 2, max = 25, groups = {New.class, Update.class})
    private Long vendorName;
}
