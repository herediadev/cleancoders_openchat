package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.User;
import org.openchat.usercases.LoginUserRequest;
import org.openchat.usercases.LoginUserService;
import spark.Request;
import spark.Response;

import static integration.APITestSuit.UUID_PATTERN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoginUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private LoginUserService loginUserService;

    @Test
    void given_a_user_login_request_it_will_return_the_user() {
        //arrange
        given(request.body()).willReturn(createRequestJsonBody());
        given(loginUserService.execute(any(LoginUserRequest.class))).willReturn(createNewUser());
        LoginUserApi loginUserApi = new LoginUserApi(loginUserService);

        //act
        String result = loginUserApi.handle(request, response);
        JsonObject jsonResult = Json.parse(result).asObject();

        //assert
        verify(response).status(200);
        verify(response).type("application/json");
        verify(loginUserService).execute(any(LoginUserRequest.class));
        assertThat(jsonResult.getString("id", "")).matches(UUID_PATTERN);
        assertThat(jsonResult.getString("username", "")).isEqualTo("username");
        assertThat(jsonResult.getString("about", "")).isEqualTo("about");
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
