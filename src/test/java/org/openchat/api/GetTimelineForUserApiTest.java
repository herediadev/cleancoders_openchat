package org.openchat.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.api.getTimelineForUserApi.GetTimelineForUserApi;
import org.openchat.api.getTimelineForUserApi.GetTimelineForUserResponsePresenter;
import org.openchat.entities.Post;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        getTimelineForUserApi = new GetTimelineForUserApi(getTimelineFromUserIdService, new GetTimelineForUserResponsePresenter(formatDateService));
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
        List<Map<String, String>> result = getTimelineForUserApi.handle(request, response);

        //assert
        verify(request).params(eq("userId"));
        verify(response).status(200);
        verify(response).type("application/json");
        verify(getTimelineFromUserIdService).apply(anyString());
        assertThat(result.get(0).get("dateTime")).isEqualTo(formatDateService.apply(posts.get(0).getDateTime()));
        assertThat(result.get(0).get("postId")).isEqualTo(posts.get(0).getPostId());
        assertThat(result.get(0).get("text")).isEqualTo(posts.get(0).getText());
        assertThat(result.get(0).get("userId")).isEqualTo(posts.get(0).getUserId());
    }
}
