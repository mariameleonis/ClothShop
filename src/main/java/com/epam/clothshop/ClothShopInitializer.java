package com.epam.clothshop;

import com.epam.clothshop.dao.*;
import com.epam.clothshop.model.*;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class ClothShopInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        log.info("Starting to initialize sample data ...");

        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {

            Category category = new Category();

            category.setCategoryName(faker.commerce().department());

            categoryRepository.save(category);

            Vendor vendor = new Vendor();

            vendor.setVendorName(faker.funnyName().name());

            vendorRepository.save(vendor);

            User user = new User();

            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(passwordEncoder.encode("1234"));
            user.setPhone(String.valueOf(faker.phoneNumber().cellPhone()));

            userRepository.save(user);
        }

        for (int i = 0; i < 50; i++) {

            Product product = new Product();

            product.setName(faker.commerce().productName());
            product.setCategoryId((long) faker.number().numberBetween(1, 10));
            product.setPrice(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price().replaceAll(",","."))));
            product.setVendorId((long) faker.number().numberBetween(1, 10));

            productRepository.save(product);
        }

        for (int i = 0; i < 10; i++) {

            Order order = new Order();
            order.setOrderTrackingNumber(UUID.randomUUID().toString());

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId((long) faker.number().numberBetween(1, 50));
            orderItem.setSellingPrice(productRepository.getById(orderItem.getProductId()).getPrice());
            orderItem.setQuantity(faker.number().numberBetween(1, 5));

            order.add(orderItem);

            User user = userRepository.getById((long) faker.number().numberBetween(1, 10));
            user.add(order);

            userRepository.save(user);
        }

        log.info("Finish with sample data initialization.");
    }
}
