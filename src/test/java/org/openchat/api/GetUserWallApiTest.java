package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.Post;
import org.openchat.usercases.GetUserWallService;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUserWallApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private GetUserWallService getUserWallService;
    private FormatDateService formatDateService;

    @BeforeEach
    void setUp() {
        formatDateService = new FormatDateService();
    }

    @Test
    void given_a_request_when_it_asks_for_the_user_wall_it_wil_response_the_user_posts_and_the_followings_posts() {
        //arrange
        List<Post> posts = Arrays.asList(
                new Post("test_post_id_1", "test_user_id", "text", LocalDateTime.now()),
                new Post("test_post_id_2", "test_user_id", "text", LocalDateTime.now())
        );
        GetUserWallApi getUserWallApi = new GetUserWallApi(getUserWallService, new FormatDateService());
        given(request.params(anyString())).willReturn("test_user_id");
        given(getUserWallService.execute(anyString())).willReturn(posts);

        //act
        String result = getUserWallApi.handle(request, response);

        //assert
        verify(request).params(eq("userId"));
        verify(response).status(200);
        verify(response).type("application/json");
        verify(getUserWallService).execute(anyString());

        assertThat(result).isEqualTo(getJson(posts));
    }

    private String getJson(List<Post> posts) {
        JsonArray jsonResponse = new JsonArray();
        posts
                .stream()
                .map(this::createJsonPost)
                .forEach(jsonResponse::add);

        return jsonResponse.toString();
    }

    private JsonObject createJsonPost(Post post) {
        return new JsonObject()
                .add("dateTime", this.formatDateService.apply(post.getDateTime()))
                .add("postId", post.getPostId())
                .add("text", post.getText())
                .add("userId", post.getUserId());
    }
}
