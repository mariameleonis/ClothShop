package com.epam.clothshop.rest;

import com.epam.clothshop.dto.OrderDto;
import com.epam.clothshop.dto.OrderResponse;
import com.epam.clothshop.dto.UserDto;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static com.epam.clothshop.ClothShopTestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<UserDto> argumentCaptor;

    @Test
    public void testCreateUser_WhenEverythingIsOk() throws Exception {
        when(userService.createUser(argumentCaptor.capture())).thenReturn(USER_3.getId());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_USER_DTO)))
                .andExpect(status().isCreated());

        assertThat(argumentCaptor.getValue().getUsername(), is(VALID_USER_DTO.getUsername()));
    }

    @Test
    public void testCreateUser_WhenInvalidUserSupplied() throws Exception {

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_USER_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers_WhenEverythingIsOk() throws Exception {

        when(userService.getUsers()).thenReturn(USERS_LIST);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(USERS_LIST.size())))
                .andExpect(jsonPath("$[0].username", is(USER_1.getUsername())))
                .andExpect(jsonPath("$[0].id", is(USER_1.getId().intValue())))
                .andExpect(jsonPath("$[1].username", is(USER_2.getUsername())))
                .andExpect(jsonPath("$[1].id", is(USER_2.getId().intValue())))
                .andExpect(jsonPath("$[2].username", is(USER_3.getUsername())))
                .andExpect(jsonPath("$[2].id", is(USER_3.getId().intValue())));
    }

    @Test
    public void testGetUserById_WhenEverythingIsOk() throws Exception {

        when(userService.getUserById(USER_1.getId())).thenReturn(USER_1);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username", is(USER_1.getUsername())))
                .andExpect(jsonPath("$.id", is(USER_1.getId().intValue())));
    }

    @Test
    public void testGetUserById_WhenNotFound() throws Exception {

        when(userService.getUserById(42L)).thenThrow(new ResourceNotFoundException("User with id: '42' not found"));

        mockMvc.perform(get("/api/users/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser_WhenEverythingIsOk() throws Exception {
        UserDto userDto = modelMapper.map(USER_1_UPDATE, UserDto.class);

        when(userService.getUserById(USER_1.getId())).thenReturn(USER_1);
        when(userService.updateUser(userDto)).thenReturn(USER_1_UPDATE);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.email", is(USER_1_UPDATE.getEmail())))
                .andExpect(jsonPath("$.id", is(USER_1_UPDATE.getId().intValue())));
    }

    @Test
    public void testUpdateUserWithUnknownId_WhenNotFound() throws Exception {
        when(userService.updateUser(INVALID_USER_DTO_UPDATE)).thenThrow(new ResourceNotFoundException("User with id: '42' not found"));

        mockMvc.perform(put("/api/users/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_USER_DTO_UPDATE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser_WhenInvalidUserSupplied() throws Exception {
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_USER_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser_WhenEverythingIsOk() throws Exception {

        when(userService.getUserById(USER_1.getId())).thenReturn(USER_1);

        mockMvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser_WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("User with id: '42' not found")).when(userService).deleteUserById(42L);
        mockMvc.perform(delete("/api/users/42")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_WhenInvalidArgumentSupplied() throws Exception {

        mockMvc.perform(delete("/api/users/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddOrderToUser_WhenEverythingIsOk() throws Exception {

        String orderTrackingNumber = UUID.randomUUID().toString();
        OrderResponse orderResponse = new OrderResponse(orderTrackingNumber);

        when(userService.addOrderToUser(anyLong(), any(OrderDto.class))).thenReturn(orderResponse);

        mockMvc.perform(post("/api/users/{id}/orders", USER_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_ORDER_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.orderTrackingNumber", is(orderTrackingNumber)));
    }

    @Test
    public void testAddOrderToUser_WhenUserNotFound() throws Exception {

        when(userService.addOrderToUser(anyLong(), any(OrderDto.class))).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(post("/api/users/{id}/orders", USER_1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_ORDER_DTO)))
                .andExpect(status().isNotFound());


    }

    @Test
    public void testAddOrderToUser_WhenInvalidOrderSupplied() throws Exception {

        mockMvc.perform(post("/api/users/{id}/orders", USER_1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_ORDER_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginUser_WhenEverythingIsOk() throws Exception {

        UserDto userDto = new UserDto(USER_1.getUsername(), USER_1.getPassword());

        when(userService.loadUserByUsername(userDto.getUsername()))
                .thenReturn(new org.springframework.security.core.userdetails.User(USER_1.getUsername(), passwordEncoder.encode(USER_1.getPassword()), new ArrayList<>()));

        when(userService.getUserByUsername(userDto.getUsername())).thenReturn(USER_1);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(USER_1.getId().intValue())))
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
    }

    @Test
    public void testLoginUser_WhenInvalidCredentialsSupplied() {

    }
}
