package com.epam.clothshop;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;

import java.math.BigDecimal;
import java.util.List;

public class ClothShopTestData {
    public static final Product PRODUCT_1 = new Product(1L, "Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L);
    public static final Product PRODUCT_2 = new Product(2L, "Floral Cocktail Dress", BigDecimal.valueOf(110.70), 10, 1L, 1L);
    public static final Product PRODUCT_3 = new Product(3L, "Blue Polka-Dot Dress", BigDecimal.valueOf(100.90), 19, 1L, 1L);

    public static final Vendor VENDOR_1 = new Vendor(1L, "H&M", List.of(PRODUCT_1, PRODUCT_2, PRODUCT_3));
    public static final Vendor VENDOR_2 = new Vendor(2L, "Zara");

    public static final Vendor VENDOR_2_UPDATE = new Vendor(2L, "Mango");
    public static final Vendor VENDOR_3 = new Vendor(3L, "Bershka");

    public static final ProductDto VALID_PRODUCT_DTO = new ProductDto("Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L);
}
