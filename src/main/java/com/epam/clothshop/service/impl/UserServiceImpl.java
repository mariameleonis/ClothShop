package com.epam.clothshop.service.impl;

import com.epam.clothshop.dao.UserRepository;
import com.epam.clothshop.dto.OrderDto;
import com.epam.clothshop.dto.OrderResponse;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.OrderItem;
import com.epam.clothshop.model.User;
import com.epam.clothshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: '%s' not found", id)));
    }

    @Override
    public Long createUser(UserDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(convertDtoToEntity(userDto)).getId();
    }

    private User convertDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public User updateUser(UserDto userDto) {
        getUserById(userDto.getId());
        return userRepository.save(convertDtoToEntity(userDto));
    }

    @Override
    public void deleteUserById(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderResponse addOrderToUser(Long userId, OrderDto orderDto) {

        Order order = new Order();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        getUserById(userId).add(order);

        Set<OrderItem> orderItems = orderDto.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        getUserById(userId).add(order);

        return new OrderResponse(orderTrackingNumber);
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
