package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static integration.APITestSuit.UUID_PATTERN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateNewUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private CreateNewUserService createNewUserService;

    @Mock
    private FindUserByUsernameService findUserByUsernameService;

    @Test
    void given_the_request_and_response_it_will_response_the_new_created_user() {
        //arrange
        given(request.body()).willReturn(createRequestJsonBody());
        given(createNewUserService.execute(any(CreateNewUserRequest.class))).willReturn(createNewUser());

        CreateNewUserApi createNewUserApi = new CreateNewUserApi(createNewUserService, new FindUserByUsernameService(new InMemoryUserRepository()));

        //act
        String result = createNewUserApi.handle(request, response);
        JsonObject jsonResult = Json.parse(result).asObject();

        //assert
        verify(response).status(201);
        verify(response).type("application/json");
        assertThat(jsonResult.getString("id", "")).matches(UUID_PATTERN);
        assertThat(jsonResult.getString("username", "")).isEqualTo("username");
        assertThat(jsonResult.getString("about", "")).isEqualTo("about");
    }

    @Test
    void given_the_same_username_it_will_throw_an_userAlreadyExistException() {
        //arrange
        User userFound = new User("id", "username", "about");
        given(request.body()).willReturn(createRequestJsonBody());
        given(findUserByUsernameService.execute(anyString())).willReturn(userFound);

        CreateNewUserApi createNewUserApi = new CreateNewUserApi(createNewUserService, findUserByUsernameService);

        //act
        String userAlreadyExist = createNewUserApi.handle(request, response);

        //assert
        verify(response).status(400);
        assertThat(userAlreadyExist).isEqualTo("Username already in use.");
    }

    private User createNewUser() {
        return new User("aaaaaaaa-ffff-ffff-ffff-aaaaaaaaaaaa", "username", "about");
    }

    private String createRequestJsonBody() {
        return new JsonObject()
                .add("username", "username")
                .add("password", "password")
                .add("about", "about")
                .toString();
    }
}