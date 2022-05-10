package com.epam.clothshop.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDto extends Dto {

    interface UpdateImage extends Exists {
    }

    @Null(groups = {New.class})
    @NotNull(groups = {Exists.class})
    private Long id;

    @NotEmpty(groups = {New.class, Update.class})
    @Null(groups = {UpdateImage.class})
    @Size(min = 4, max = 100, groups = {New.class, Update.class})
    private String name;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class})
    @DecimalMin(value = "0.0", inclusive = false, groups = {New.class, Update.class})
    @Digits(integer=4, fraction=2, groups = {New.class, Update.class})
    private BigDecimal price;

    @NotNull(groups = {UpdateImage.class})
    private String imageFileName;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class})
    private int unitsInStock;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class})
    private Long categoryId;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class})
    private Long vendorId;
}
