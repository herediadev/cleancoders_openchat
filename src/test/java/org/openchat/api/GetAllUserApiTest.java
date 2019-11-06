package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.User;
import org.openchat.usercases.FindAllUserService;
import spark.Request;
import spark.Response;

import static integration.APITestSuit.UUID_PATTERN;
import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class GetAllUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private FindAllUserService findAllUserService;

    private GetAllUserApi getAllUserApi;

    @BeforeEach
    void setUp() {
        getAllUserApi = new GetAllUserApi(findAllUserService);
    }

    @Test
    void given_a_request_it_will_return_all_the_users() {
        //arrange
        given(findAllUserService.execute()).willReturn(singletonList(createNewUser()));

        //act
        String users = getAllUserApi.handle(request, response);
        JsonArray userJsonList = Json.parse(users).asArray();
        JsonObject jsonUser = Json.parse(userJsonList.get(0).asString()).asObject();

        //assert
        verify(response).status(200);
        verify(response).type("application/json");
        verify(findAllUserService).execute();
        Assertions.assertThat(userJsonList.size()).isEqualTo(1);
        Assertions.assertThat(jsonUser.getString("id", "")).matches(UUID_PATTERN);
        Assertions.assertThat(jsonUser.getString("username", "")).isEqualTo("username");
        Assertions.assertThat(jsonUser.getString("about", "")).isEqualTo("about");
    }

    private User createNewUser() {
        return new User("aaaaaaaa-ffff-ffff-ffff-aaaaaaaaaaaa", "username", "password", "about");
    }
}
