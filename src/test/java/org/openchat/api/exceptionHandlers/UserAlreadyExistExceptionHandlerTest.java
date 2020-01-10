package org.openchat.api.exceptionHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.usercases.exceptions.UserAlreadyExistException;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserAlreadyExistExceptionHandlerTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    void given_the_same_user_name_it_will_get_the_response_from_the_exception() {
        //arrange
        UserAlreadyExistException userAlreadyExistException = new UserAlreadyExistException("test_user_name");
        UserAlreadyExistExceptionHandler userAlreadyExistExceptionHandler = new UserAlreadyExistExceptionHandler();

        //act
        userAlreadyExistExceptionHandler.handle(userAlreadyExistException, request, response);

        //assert
        verify(response).status(400);
        verify(response).body("user test_user_name already exists");
    }

}