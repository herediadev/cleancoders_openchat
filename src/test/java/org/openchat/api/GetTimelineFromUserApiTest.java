package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.Post;
import org.openchat.usercases.GetTimelineFromUserIdService;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetTimelineFromUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private GetTimelineFromUserIdService getTimelineFromUserIdService;

    private FormatDateService formatDateService;

    @BeforeEach
    void setUp() {
        formatDateService = new FormatDateService();
    }

    @Test
    void given_a_request_when_it_asks_for_the_user_post_it_will_response_the_post_in_reverse_order() {
        //arrange
        List<Post> posts = Arrays.asList(
                new Post("test_post_id_1", "test_user_id", "text", LocalDateTime.now()),
                new Post("test_post_id_2", "test_user_id", "text", LocalDateTime.now())
        );
        GetTimelineFromUserApi getTimelineFromUserApi = new GetTimelineFromUserApi(getTimelineFromUserIdService, formatDateService);
        given(request.params(anyString())).willReturn("test_user_id");
        given(getTimelineFromUserIdService.execute(anyString())).willReturn(posts);


        //act
        String result = getTimelineFromUserApi.handle(request, response);


        //assert
        verify(request).params(eq("userId"));
        verify(response).status(200);
        verify(response).type("application/json");
        verify(getTimelineFromUserIdService).execute(anyString());
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
                .add("dateTime", formatDateService.execute(post.getDateTime()))
                .add("postId", post.getPostId())
                .add("text", post.getText())
                .add("userId", post.getUserId());
    }
}
