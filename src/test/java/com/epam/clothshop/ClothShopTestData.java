package com.epam.clothshop;

import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.User;
import com.epam.clothshop.model.Vendor;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public static final User USER_1 = new User(1L, "mariya888", "Mariya", "Russakova", "russakova.m@gmail.com", "12345", "77056081966", null);

    public static final User USER_1_UPDATE = new User(1L, "mariya888", "Mariya", "Ivanova", "ivanova.m@gmail.com", "12345", "77056081966", null);
    public static final User USER_2 = new User(2L, "anna777", "Anna", "Russakova", "russakova.a@gmail.com", "12345", "77056081967", null);

    public static final User USER_3 = new User(3L, "svetlana999", "Svetlana", "Petrova", "petrova.s@gmail.com", "12345", "77056056789", null);

    public static final List<User> USERS_LIST = new ArrayList<>(List.of(USER_1, USER_2, USER_3));

    public static final UserDto VALID_USER_DTO = new UserDto("svetlana999", "Svetlana", "Petrova", "petrova.s@gmail.com", "12345", "77056056789");

    public static final UserDto INVALID_USER_DTO_UPDATE = new UserDto(42L, "svetlana999", "Svetlana", "Petrova", "petrova.s@gmail.com", "12345", "77056056789");
    public static final UserDto INVALID_USER_DTO = new UserDto("", "", "", "", "", "");
}
