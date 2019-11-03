package org.openchat.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateNewUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    void given_the_request_and_response_it_will_response_the_new_created_user() {
        //arrange
        CreateNewUserApi createNewUserApi = new CreateNewUserApi();
        given(request.body()).willReturn(getJson());

        //act
        createNewUserApi.handle(request, response);

        //assert
        verify(response).status(201);
    }

    private String getJson() {
        return "{\n" +
                "  \"password\": \"password\",\n" +
                "  \"about\": \"about\",\n" +
                "  \"username\": \"username\"\n" +
                "}";
    }
}