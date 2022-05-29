package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDto extends Dto {

    public interface UpdateImage extends Exists {
    }

    public interface AddToVendor {
    }

    @Null(groups = {New.class, AddToVendor.class})
    @NotNull(groups = {Exists.class})
    private Long id;

    @NotEmpty(groups = {New.class, Update.class, AddToVendor.class})
    @Null(groups = {UpdateImage.class})
    @Size(min = 4, max = 100, groups = {New.class, Update.class, AddToVendor.class})
    private String name;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class, AddToVendor.class})
    @DecimalMin(value = "0.0", inclusive = false, groups = {New.class, Update.class, AddToVendor.class})
    @Digits(integer=4, fraction=2, groups = {New.class, Update.class, AddToVendor.class})
    private BigDecimal price;

    @NotNull(groups = {UpdateImage.class})
    private String imageFileName;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class, AddToVendor.class})
    private int unitsInStock;

    @Null(groups = {UpdateImage.class})
    @NotNull(groups = {New.class, Update.class, AddToVendor.class})
    private Long categoryId;

    @Null(groups = {UpdateImage.class, AddToVendor.class})
    @NotNull(groups = {New.class, Update.class})
    private Long vendorId;

    @Tolerate
    public ProductDto(String name, BigDecimal price, int unitsInStock, Long categoryId, Long vendorId) {
        this.name = name;
        this.price = price;
        this.unitsInStock = unitsInStock;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
    }

    @Tolerate
    public ProductDto(Long id, String name, BigDecimal price, int unitsInStock, Long categoryId, Long vendorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitsInStock = unitsInStock;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
    }
}
