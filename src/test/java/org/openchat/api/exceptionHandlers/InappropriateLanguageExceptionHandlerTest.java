package org.openchat.api.exceptionHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InappropriateLanguageExceptionHandlerTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    void given_the_new_post_with_an_inappropriate_word_it_will_get_the_response_exception() {
        //arrange
        InappropriateLanguageException inappropriateLanguageException = new InappropriateLanguageException();
        InappropriateLanguageExceptionHandler inappropriateLanguageExceptionHandler = new InappropriateLanguageExceptionHandler();

        //act
        inappropriateLanguageExceptionHandler.handle(inappropriateLanguageException, request, response);

        //assert
        verify(response).status(400);
        verify(response).body("Post contains inappropriate language.");
    }

}