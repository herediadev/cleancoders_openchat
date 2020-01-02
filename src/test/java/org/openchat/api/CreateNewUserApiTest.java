package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.User;
import org.openchat.usercases.CreateNewUserRequest;
import org.openchat.usercases.exceptions.UserAlreadyExistException;
import spark.Request;
import spark.Response;

import java.util.function.Consumer;
import java.util.function.Function;

import static integration.APITestSuit.UUID_PATTERN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class CreateNewUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Spy
    private Function<CreateNewUserRequest, User> createNewUserService;

    @Spy
    private Consumer<String> validaIfUserAlreadyExistService;

    private CreateNewUserApi createNewUserApi;

    @BeforeEach
    void setUp() {
        createNewUserApi = new CreateNewUserApi(createNewUserService, validaIfUserAlreadyExistService, new CreateNewUserRequestService(), new CreateNewUserResponsePresenter());
    }

    @Test
    void given_the_request_and_response_it_will_response_the_new_created_user() {
        //arrange
        doReturn(createRequestJsonBody()).when(request).body();
        doReturn(createNewUser()).when(createNewUserService).apply(any(CreateNewUserRequest.class));

        //act
        String result = createNewUserApi.handle(request, response);
        JsonObject jsonResult = Json.parse(result).asObject();

        //assert
        verify(response).status(201);
        verify(response).type("application/json");
        verify(createNewUserService).apply(any(CreateNewUserRequest.class));
        verify(validaIfUserAlreadyExistService).accept(anyString());
        assertThat(jsonResult.getString("id", "")).matches(UUID_PATTERN);
        assertThat(jsonResult.getString("username", "")).isEqualTo("username");
        assertThat(jsonResult.getString("about", "")).isEqualTo("about");
    }

    @Test
    void given_the_same_username_it_will_throw_an_userAlreadyExistException() {
        //arrange
        doReturn(createRequestJsonBody()).when(request).body();
        doThrow(new UserAlreadyExistException("user already exists")).when(validaIfUserAlreadyExistService).accept(anyString());

        //act
        String userAlreadyExist = createNewUserApi.handle(request, response);

        //assert
        verify(response).status(400);
        assertThat(userAlreadyExist).isEqualTo("Username already in use.");
    }

    private User createNewUser() {
        return new User("aaaaaaaa-ffff-ffff-ffff-aaaaaaaaaaaa", "username", "password", "about");
    }

    private String createRequestJsonBody() {
        return new JsonObject()
                .add("username", "username")
                .add("password", "password")
                .add("about", "about")
                .toString();
    }
}