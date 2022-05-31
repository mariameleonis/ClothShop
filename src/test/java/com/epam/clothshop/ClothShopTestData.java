package com.epam.clothshop;

import com.epam.clothshop.dto.OrderItemDto;
import com.epam.clothshop.dto.ProductDto;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.model.*;
import org.aspectj.weaver.ast.Or;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClothShopTestData {
    public static final Product PRODUCT_1 = new Product(1L, "Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L);
    public static final Product PRODUCT_2 = new Product(2L, "Floral Cocktail Dress", BigDecimal.valueOf(110.70), 10, 1L, 1L);
    public static final Product PRODUCT_3 = new Product(3L, "Blue Polka-Dot Dress", BigDecimal.valueOf(100.90), 19, 1L, 1L);
    public static final List<Product> PRODUCT_LIST = new ArrayList<>(List.of(PRODUCT_1, PRODUCT_2, PRODUCT_3));
    public static final Product PRODUCT_1_UPDATE = new Product(1L, "Little Red Dress", BigDecimal.valueOf(113.90), 10, 1L, 1L);
    public static final Vendor VENDOR_1 = new Vendor(1L, "H&M", List.of(PRODUCT_1, PRODUCT_2, PRODUCT_3));
    public static final Vendor VENDOR_2 = new Vendor(2L, "Zara");

    public static final Vendor VENDOR_2_UPDATE = new Vendor(2L, "Mango");
    public static final Vendor VENDOR_3 = new Vendor(3L, "Bershka");

    public static final ProductDto VALID_PRODUCT_DTO_ADD_TO_VENDOR = new ProductDto("Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, null);

    public static final ProductDto VALID_PRODUCT_DTO = new ProductDto("Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L);
    public static final ProductDto INVALID_PRODUCT_DTO = new ProductDto("", BigDecimal.valueOf(120.50), 15, 1L, 1L);
    public static final ProductDto INVALID_PRODUCT_DTO_UPDATE = new ProductDto(42L, "Little Black Dress", BigDecimal.valueOf(120.50), 15, 1L, 1L);
    public static final User USER_1 = new User(1L, "mariya888", "Mariya", "Russakova", "russakova.m@gmail.com", "12345", "77056081966", null);

    public static final User USER_1_UPDATE = new User(1L, "mariya888", "Mariya", "Ivanova", "ivanova.m@gmail.com", "12345", "77056081966", null);
    public static final User USER_2 = new User(2L, "anna777", "Anna", "Russakova", "russakova.a@gmail.com", "12345", "77056081967", null);

    public static final User USER_3 = new User(3L, "svetlana999", "Svetlana", "Petrova", "petrova.s@gmail.com", "12345", "77056056789", null);

    public static final List<User> USERS_LIST = new ArrayList<>(List.of(USER_1, USER_2, USER_3));
    public static final UserDto VALID_USER_DTO = new UserDto("svetlana999", "Svetlana", "Petrova", "petrova.s@gmail.com", "12345", "77056056789");
    public static final UserDto INVALID_USER_DTO_UPDATE = new UserDto(42L, "svetlana999", "Svetlana", "Petrova", "petrova.s@gmail.com", "12345", "77056056789");
    public static final UserDto INVALID_USER_DTO = new UserDto("", "", "", "", "", "");
    public static final OrderItem ORDER_ITEM_1 = new OrderItem(1L, 1L,1,BigDecimal.valueOf(120.50));
    public static final OrderItem ORDER_ITEM_2 = new OrderItem(2L, 2L,2,BigDecimal.valueOf(110.70));
    public static final OrderItem ORDER_ITEM_3 = new OrderItem(3L, 3L,1,BigDecimal.valueOf(100.90));
    public static final Order ORDER_1 = new Order(1L, USER_1, BigDecimal.valueOf(120.50), Set.of(ORDER_ITEM_1));
    public static final Order ORDER_2 = new Order(2L, USER_2, BigDecimal.valueOf(110.70), Set.of(ORDER_ITEM_2));
    public static final Order ORDER_3 = new Order(3L, USER_3, BigDecimal.valueOf(100.90), Set.of(ORDER_ITEM_3));
    public static final List<Order> ORDER_LIST = new ArrayList<>(List.of(ORDER_1, ORDER_2, ORDER_3));

    public static final OrderItemDto VALID_ORDER_ITEM_DTO = new OrderItemDto(1L, 2, BigDecimal.valueOf(120.50));
    public static final OrderItemDto INVALID_ORDER_ITEM_DTO = new OrderItemDto(null, 0, null);
}
