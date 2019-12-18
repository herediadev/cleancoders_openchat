package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.Post;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTimelineForUserApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Spy
    private Function<String, List<Post>> getTimelineFromUserIdService;

    private FormatDateService formatDateService;

    private GetTimelineForUserApi getTimelineForUserApi;

    @BeforeEach
    void setUp() {
        formatDateService = new FormatDateService();
        getTimelineForUserApi = new GetTimelineForUserApi(getTimelineFromUserIdService, new CreateTimelineForUserResponseService(formatDateService));
    }

    @Test
    void given_a_request_when_it_asks_for_the_user_post_it_will_response_the_post_in_reverse_order() {
        //arrange
        List<Post> posts = Arrays.asList(
                new Post("test_post_id_1", "test_user_id", "text", LocalDateTime.of(2018, 1, 10, 11, 30, 0)),
                new Post("test_post_id_2", "test_user_id", "text", LocalDateTime.of(2018, 1, 10, 11, 35, 0))
        );
        doReturn("test_user_id").when(request).params(anyString());
        doReturn(posts).when(getTimelineFromUserIdService).apply(anyString());

        //act
        String result = getTimelineForUserApi.handle(request, response);

        //assert
        verify(request).params(eq("userId"));
        verify(response).status(200);
        verify(response).type("application/json");
        verify(getTimelineFromUserIdService).apply(anyString());
        assertThat(result).isEqualTo(getJson(posts));
    }

    private String getJson(List<Post> posts) {
        JsonArray jsonResponse = new JsonArray();
        posts.stream()
                .map(this::createJsonPost)
                .forEach(jsonResponse::add);

        return jsonResponse.toString();
    }

    private JsonObject createJsonPost(Post post) {
        return new JsonObject()
                .add("dateTime", formatDateService.apply(post.getDateTime()))
                .add("postId", post.getPostId())
                .add("text", post.getText())
                .add("userId", post.getUserId());
    }
}
