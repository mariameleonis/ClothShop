package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDto extends Dto {

    public interface AddToVendor {
    }

    @Null(groups = {New.class, AddToVendor.class})
    @NotNull(groups = {Exists.class})
    private Long id;

    @NotEmpty(groups = {New.class, Update.class, AddToVendor.class})
    @Size(min = 4, max = 100, groups = {New.class, Update.class, AddToVendor.class})
    private String name;

    @NotNull(groups = {New.class, Update.class, AddToVendor.class})
    @DecimalMin(value = "0.0", inclusive = false, groups = {New.class, Update.class, AddToVendor.class})
    @Digits(integer=4, fraction=2, groups = {New.class, Update.class, AddToVendor.class})
    private BigDecimal price;

    @NotNull(groups = {New.class, Update.class, AddToVendor.class})
    private Long categoryId;

    @Null(groups = {AddToVendor.class})
    @NotNull(groups = {New.class, Update.class})
    private Long vendorId;

    @Tolerate
    public ProductDto(String name, BigDecimal price, Long categoryId, Long vendorId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
    }

    @Tolerate
    public ProductDto(Long id, String name, BigDecimal price, Long categoryId, Long vendorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
    }
}
