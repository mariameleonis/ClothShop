package com.epam.clothshop.service;

import com.epam.clothshop.dto.OrderDto;
import com.epam.clothshop.dto.OrderResponse;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(long id);
    Long createUser(UserDto userDto);
    User updateUser(UserDto userDto);
    void deleteUserById(Long id);
    OrderResponse addOrderToUser(Long userId, OrderDto orderDto);
}
