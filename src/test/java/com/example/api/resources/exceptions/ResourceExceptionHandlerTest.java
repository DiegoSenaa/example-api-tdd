package com.example.api.resources.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.example.api.services.exceptions.DataIntegratyVioletionException;
import com.example.api.services.exceptions.ObjectNotFoundException;

public class ResourceExceptionHandlerTest {

    private static final String OBJECT_MESSAGE = "Objeto não encontrado";
    private static final String DATA_INTEGRATY_MESSAGE = "E-mail já existe no sistema!";

    @InjectMocks
    ResourceExceptionHandler resourceExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenDataIntegratyVioletionThenReturnResponseEntity() {
        ResponseEntity<StandartError> response = resourceExceptionHandler
                .dataIntegratyVioletion(new DataIntegratyVioletionException(DATA_INTEGRATY_MESSAGE), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandartError.class, response.getBody().getClass());
        assertEquals(DATA_INTEGRATY_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    void whenObjectNotFoundThenReturnResponseEntity() {
        ResponseEntity<StandartError> response = resourceExceptionHandler
                .objectNotFound(new ObjectNotFoundException(OBJECT_MESSAGE), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandartError.class, response.getBody().getClass());
        assertEquals(OBJECT_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }
}
