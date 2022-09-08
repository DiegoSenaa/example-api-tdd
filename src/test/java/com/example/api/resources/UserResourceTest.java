package com.example.api.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.api.domain.User;
import com.example.api.domain.dtos.UserDTO;
import com.example.api.services.impl.UserServiceImpl;

public class UserResourceTest {

    private static final int INDEX = 0;
    private static final String EMAIL = "diego@teste.com";
    private static final String PASSWORD = "123";
    private static final String NAME = "Diego";
    private static final Integer ID = 1;

    @InjectMocks
    UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        startUser();
    }

    @Test
    void whenDeleteThenReturnAnSucess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<Void> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyInt());
    }

    @Test
    void whenUpdateThenReturnSucess() {
        when(service.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(NAME, response.getBody().getName());
    }

    @Test
    void whenFindAllThenReturnSucess() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().size());
        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
    }

    @Test
    void whenFindByIdThenReturnSucess() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(NAME, response.getBody().getName());
    }

    @Test
    void whenSaveReturnSucess() {
        when(service.save(userDTO)).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.save(userDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    private void startUser() {
        user = new User(ID, NAME, PASSWORD, EMAIL);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}
