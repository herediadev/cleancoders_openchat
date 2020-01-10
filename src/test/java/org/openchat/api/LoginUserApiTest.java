package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.api.createNewUserApi.CreateNewUserResponsePresenter;
import org.openchat.entities.User;
import org.openchat.usercases.LoginUserRequest;
import org.openchat.usercases.exceptions.InvalidCredentialException;
import spark.Request;
import spark.Response;

import java.util.function.Function;

import static integration.APITestSuit.UUID_PATTERN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Spy
    private Function<LoginUserRequest, User> loginUserService;

    private LoginUserApi loginUserApi;

    @BeforeEach
    void setUp() {
        CreateNewUserResponsePresenter createNewUserResponsePresenter = new CreateNewUserResponsePresenter();
        loginUserApi = new LoginUserApi(loginUserService, createNewUserResponsePresenter);
    }

    @Test
    void given_a_user_login_request_it_will_return_the_user() {
        //arrange
        doReturn(createRequestJsonBody()).when(request).body();
        doReturn(createNewUser()).when(loginUserService).apply(any(LoginUserRequest.class));

        //act
        JsonObject jsonResult = loginUserApi.handle(request, response);

        //assert
        verify(response).status(200);
        verify(response).type("application/json");
        verify(loginUserService).apply(any(LoginUserRequest.class));
        assertThat(jsonResult.getString("id", "")).matches(UUID_PATTERN);
        assertThat(jsonResult.getString("username", "")).isEqualTo("username");
        assertThat(jsonResult.getString("about", "")).isEqualTo("about");
    }

    @Test
    void given_the_request_with_invalid_password_it_will_response_invalid_credential() {
        //arrange
        doReturn(createRequestJsonBody()).when(request).body();
        doThrow(InvalidCredentialException.class).when(loginUserService).apply(any(LoginUserRequest.class));

        //act and assert
        org.junit.jupiter.api.Assertions.assertThrows(InvalidCredentialException.class, () -> loginUserApi.handle(request, response));
    }

    @Test
    void given_the_exception_handler_register_it_will_check_the_response() {
        //arrange
        InvalidCredentialException invalidCredentialException = new InvalidCredentialException();

        //act
        LoginUserApi.registerExceptionHandler(invalidCredentialException, request, response);

        //assert
        verify(response).status(404);
        verify(response).body("Invalid credentials.");
    }

    private User createNewUser() {
        return new User("aaaaaaaa-ffff-ffff-ffff-aaaaaaaaaaaa", "username", "password", "about");
    }

    private String createRequestJsonBody() {
        return new JsonObject()
                .add("username", "username")
                .add("password", "password")
                .toString();
    }
}
