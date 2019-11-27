package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.User;
import org.openchat.usercases.FindUserByIdService;
import org.openchat.usercases.GetAllFollowingForUserService;
import spark.Request;
import spark.Response;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetAllFollowingForUserApiTest {
    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private GetAllFollowingForUserService getAllFollowingForUserService;

    @Mock
    private FindUserByIdService findUserByIdService;

    @Test
    void given_the_request_when_calling_the_get_all_following_for_user_it_will_response_the_list_of_the_followings() {
        //arrange
        User user = new User("test_id", "test_name", "test_password", "test_about");
        GetAllFollowingForUserApi getAllFollowingForUser = new GetAllFollowingForUserApi(getAllFollowingForUserService, findUserByIdService);
        given(request.params("followerId")).willReturn("test_id");
        given(findUserByIdService.execute(anyString())).willReturn(user);
        given(getAllFollowingForUserService.execute(eq(user.getUsername()))).willReturn(Collections.singletonList(user));

        //act
        String result = getAllFollowingForUser.handle(request, response);

        //assert
        Mockito.verify(response).status(200);
        Mockito.verify(response).type("application/json");
        Mockito.verify(getAllFollowingForUserService).execute(anyString());
        Mockito.verify(findUserByIdService).execute(anyString());
        Assertions.assertThat(result).isEqualTo(JsonContaining(user));
    }

    private String JsonContaining(User user) {
        JsonArray jsonValues = new JsonArray();

        jsonValues.add(
                new JsonObject()
                        .add("id", user.getId())
                        .add("username", user.getUsername())
                        .add("about", user.getAbout())
        );

        return jsonValues.toString();
    }
}
