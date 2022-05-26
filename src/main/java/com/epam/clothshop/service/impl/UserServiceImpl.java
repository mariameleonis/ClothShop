package com.epam.clothshop.service.impl;

import com.epam.clothshop.dao.UserRepository;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.model.User;
import com.epam.clothshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;
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

        return userRepository.save(convertDtoToEntity(userDto)).getId();
    }

    private User convertDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public User updateUser(UserDto userDto) {

        return userRepository.save(convertDtoToEntity(userDto));
    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }
}
