package com.example.api.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.modelmapper.ModelMapper;

import com.example.api.domain.User;
import com.example.api.domain.dtos.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.exceptions.DataIntegratyVioletionException;
import com.example.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class UserServiceImplTest {

    private static final int INDEX = 0;
    private static final String EMAIL = "diego@teste.com";
    private static final String PASSWORD = "123";
    private static final String NAME = "Diego";
    private static final Integer ID = 1;
    private static final String OBJECT_MESSAGE = "Objeto não encontrado";
    private static final String DATA_INTEGRATY_MESSAGE = "E-mail já existe no sistema!";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(NAME, response.getName());

    }

    @Test
    void whenFindByIdThenReturnAnExceptionObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_MESSAGE));

        try {
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfuser() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(User.class, response.get(INDEX).getClass());
        assertEquals(ID, response.size());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
        assertEquals(NAME, response.get(INDEX).getName());
    }

    @Test
    void whenCreateThenReturnAnSucess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.save(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(NAME, response.getName());
    }

    @Test
    void whenCreateThenReturnAnDataIntegratyVioletionException() {

        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            service.save(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyVioletionException.class, ex.getClass());
            assertEquals(DATA_INTEGRATY_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void whenCreateWithDifferentIdForAExistentEmailThenReturnAnDataIntegratyVioletionException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            userDTO.setId(2);
            service.save(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyVioletionException.class, ex.getClass());
            assertEquals(DATA_INTEGRATY_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnAnDataIntegratyVioletionException() {

        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            service.update(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyVioletionException.class, ex.getClass());
            assertEquals(DATA_INTEGRATY_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void whenUpdateWithDifferentIdForAExistentEmailThenReturnAnDataIntegratyVioletionException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            userDTO.setId(2);
            service.update(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyVioletionException.class, ex.getClass());
            assertEquals(DATA_INTEGRATY_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void whenDeleteThenReturnAnSucess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);

        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteThenReturnAnExceptionObjectNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_MESSAGE));

        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_MESSAGE, ex.getMessage());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, PASSWORD, EMAIL);
        userDTO = new UserDTO(ID, NAME, PASSWORD, EMAIL);
        optionalUser = Optional.of(new User(ID, NAME, PASSWORD, EMAIL));

    }
}
