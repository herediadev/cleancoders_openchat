package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.api.createNewPostApi.CreateNewPostApi;
import org.openchat.api.createNewPostApi.CreateNewPostRequestService;
import org.openchat.api.createNewPostApi.CreateNewPostResponsePresenter;
import org.openchat.entities.Post;
import org.openchat.usercases.CreatePostRequest;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNewPostApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Spy
    private Function<CreatePostRequest, Post> createNewPostService;

    @Spy
    private FormatDateService formatDateService;

    private CreateNewPostApi createNewPostApi;

    @BeforeEach
    void setUp() {
        createNewPostApi = new CreateNewPostApi(createNewPostService, new CreateNewPostRequestService(), new CreateNewPostResponsePresenter(formatDateService));

    }

    @Test
    void given_a_request_when_create_new_post_it_will_response_the_post_created() {
        //arrange
        Post postCreated = new Post(
                "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
                "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
                "text",
                LocalDateTime.of(2018, 1, 10, 11, 30, 0));
        doReturn("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx").when(request).params("userId");
        doReturn(getBody()).when(request).body();
        doReturn(postCreated).when(createNewPostService).apply(any(CreatePostRequest.class));

        //act
        JsonObject result = createNewPostApi.handle(request, response);

        //assert
        verify(createNewPostService).apply(any(CreatePostRequest.class));
        verify(formatDateService).apply(any(LocalDateTime.class));
        verify(response).type("application/json");
        verify(response).status(201);
        assertThat(result).isEqualTo(getJsonResult(postCreated));
    }

    @Test
    void given_a_request_when_creating_a_new_post_with_inappropriate_words_it_will_response_an_error() {
        //arrange
        doReturn("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx").when(request).params("userId");
        doReturn(getBody()).when(request).body();
        doThrow(InappropriateLanguageException.class).when(createNewPostService).apply(any(CreatePostRequest.class));

        //act and assert
        Assertions.assertThrows(InappropriateLanguageException.class, () -> createNewPostApi.handle(request, response));
    }

    @Test
    void given_the_new_post_with_an_inappropriate_word_it_will_get_the_response_exception() {
        //arrange
        InappropriateLanguageException inappropriateLanguageException = new InappropriateLanguageException();

        //act
        CreateNewPostApi.registerExceptionHandler(inappropriateLanguageException, request, response);

        //assert
        verify(response).status(400);
        verify(response).body("Post contains inappropriate language.");
    }

    private String getBody() {
        return new JsonObject()
                .add("text", "text")
                .toString();
    }

    private JsonObject getJsonResult(Post postCreated) {
        return new JsonObject()
                .add("dateTime", "2018-01-10T11:30:00Z")
                .add("postId", postCreated.getPostId())
                .add("text", postCreated.getText())
                .add("userId", postCreated.getUserId());
    }
}