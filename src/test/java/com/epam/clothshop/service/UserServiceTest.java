package com.epam.clothshop.service;

import com.epam.clothshop.dao.UserRepository;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.dto.VendorDto;
import com.epam.clothshop.model.User;
import com.epam.clothshop.model.Vendor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

        assertThat(resultUsers.size(), is(USERS_LIST.size()));
        assertThat(resultUsers.get(0).getFirstName(), is(USER_1.getFirstName()));
        assertThat(resultUsers.get(0).getLastName(), is(USER_1.getLastName()));
        assertThat(resultUsers.get(1).getFirstName(), is(USER_2.getFirstName()));
        assertThat(resultUsers.get(1).getLastName(), is(USER_2.getLastName()));
        assertThat(resultUsers.get(3).getFirstName(), is(USER_3.getFirstName()));
        assertThat(resultUsers.get(3).getLastName(), is(USER_3.getLastName()));
    }

    @Test
    public void testGetUserById() {

        when(userRepository.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));

        User resultUser = userService.getUserById(USER_1.getId());

        assertThat(resultUser.getId(), is(USER_1.getId()));
        assertThat(resultUser.getFirstName(), is(USER_1.getFirstName()));
    }

    @Test
    public void testCreateUser() {

        when(userRepository.save(argumentCaptor.capture())).thenReturn(USER_3);

        Long resultUserId = userService.createUser(VALID_USER_DTO);

        assertThat(resultUserId, is(USER_3.getId()));
        assertThat(argumentCaptor.getValue().getFirstName(), is(USER_3.getFirstName()));
    }

    @Test
    public void testUpdateUser() {

        UserDto userDto = modelMapper.map(USER_1_UPDATE, UserDto.class);

        when(userRepository.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));

        User user = userService.updateUser(USER_1.getId(), userDto);

        assertThat(user.getLastName(), is(USER_1_UPDATE.getLastName()));
        assertThat(user.getEmail(), is(USER_1_UPDATE.getEmail()));
    }

    @Test
    public void testDeleteUserById() {

        when(userRepository.findById(USER_3.getId())).thenReturn(Optional.of(USER_3));

        doNothing().when(userRepository).deleteById(USER_3.getId());

        userService.deleteUserById(USER_3.getId());

        verify(userRepository, times(1)).deleteById(USER_3.getId());
    }
}
