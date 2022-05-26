package com.epam.clothshop.service;

import com.epam.clothshop.dao.UserRepository;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.model.User;
import com.epam.clothshop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testGetAllUsers() {

        when(userRepository.findAll()).thenReturn(USERS_LIST);

        List<User> resultUsers = userService.getUsers();

        assertEquals(resultUsers, USERS_LIST);
    }

    @Test
    public void testGetUserById() {

        when(userRepository.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));

        User resultUser = userService.getUserById(USER_1.getId());

        assertEquals(resultUser, USER_1);
    }

    @Test
    public void testCreateUser() {

        when(userMapper.map(VALID_USER_DTO, User.class)).thenReturn(USER_3);

        when(userRepository.save(argumentCaptor.capture())).thenReturn(USER_3);

        Long resultUserId = userService.createUser(VALID_USER_DTO);

        assertThat(resultUserId, is(USER_3.getId()));
        assertThat(argumentCaptor.getValue().getFirstName(), is(USER_3.getFirstName()));
    }

    @Test
    public void testUpdateUser() {

        UserDto userDto = modelMapper.map(USER_1_UPDATE, UserDto.class);

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(USER_1));

        when(userMapper.map(userDto, User.class)).thenReturn(USER_1_UPDATE);

        when(userRepository.save(USER_1_UPDATE)).thenReturn(USER_1_UPDATE);

        User user = userService.updateUser(userDto);

        assertEquals(user, USER_1_UPDATE);
    }

    @Test
    public void testDeleteUserById() {

        when(userRepository.findById(USER_3.getId())).thenReturn(Optional.of(USER_3));

        doNothing().when(userRepository).deleteById(USER_3.getId());

        userService.deleteUserById(USER_3.getId());

        verify(userRepository, times(1)).deleteById(USER_3.getId());
    }
}
