package org.openchat.api.exceptionHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowingAlreadyExistExceptionHandlerTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    void given_the_exception_when_it_is_thrown_it_will_get_the_exception_response() {
        //arrange
        FollowingAlreadyExistException followingAlreadyExistException = new FollowingAlreadyExistException();
        FollowingAlreadyExistExceptionHandler followingAlreadyExistExceptionHandler = new FollowingAlreadyExistExceptionHandler();

        //act
        followingAlreadyExistExceptionHandler.handle(followingAlreadyExistException, request, response);

        //arrange
        verify(response).status(400);
        verify(response).body("Following already exist.");
    }
}