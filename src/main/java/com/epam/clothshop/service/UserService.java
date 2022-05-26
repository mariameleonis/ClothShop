package com.epam.clothshop.service;

import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById();
    Long createUser();
    User updateUser(Long id, UserDto userDto);
    void deleteUserById(Long id);
}
