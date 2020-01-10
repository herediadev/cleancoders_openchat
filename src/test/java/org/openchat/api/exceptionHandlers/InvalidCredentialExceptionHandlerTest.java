package org.openchat.api.exceptionHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.usercases.exceptions.InvalidCredentialException;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvalidCredentialExceptionHandlerTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    void given_the_exception_handler_register_it_will_check_the_response() {
        //arrange
        InvalidCredentialException invalidCredentialException = new InvalidCredentialException();
        InvalidCredentialExceptionHandler invalidCredentialExceptionHandler = new InvalidCredentialExceptionHandler();

        //act
        invalidCredentialExceptionHandler.handle(invalidCredentialException, request, response);

        //assert
        verify(response).status(404);
        verify(response).body("Invalid credentials.");
    }
}