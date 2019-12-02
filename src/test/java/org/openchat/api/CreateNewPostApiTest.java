package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.Post;
import org.openchat.usercases.CreateNewPostService;
import org.openchat.usercases.CreatePostRequest;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.Request;
import spark.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateNewPostApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private CreateNewPostService createNewPostService;
    private CreateNewPostApi createNewPostApi;

    @BeforeEach
    void setUp() {
        createNewPostApi = new CreateNewPostApi(createNewPostService);
        given(request.params("userId")).willReturn("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        given(request.body()).willReturn(getBody());
    }

    @Test
    void given_a_request_when_create_new_post_it_will_response_the_post_created() {
        //arrange
        Post postCreated = new Post("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", "text", "2018-01-10T11:30:00Z");
        given(createNewPostService.execute(any(CreatePostRequest.class))).willReturn(postCreated);

        //act
        String result = createNewPostApi.handle(request, response);

        //assert
        verify(createNewPostService).execute(any(CreatePostRequest.class));
        verify(response).type("application/json");
        verify(response).status(201);
        assertThat(result).contains(getJsonResult(postCreated));
    }

    @Test
    void given_a_request_when_creating_a_new_post_with_inappropiate_words_it_will_response_an_error() {
        //arrange
        given(createNewPostService.execute(any(CreatePostRequest.class))).willThrow(new InappropriateLanguageException());

        //act
        String result = createNewPostApi.handle(request, response);

        //assert
        verify(response).status(400);
        assertThat(result).isEqualTo("Post contains inappropriate language.");
    }

    private String getBody() {
        return new JsonObject()
                .add("text", "text")
                .toString();
    }

    private String getJsonResult(Post postCreated) {
        return new JsonObject()
                .add("dateTime", postCreated.getDateTime())
                .add("postId", postCreated.getPostId())
                .add("text", postCreated.getText())
                .add("userId", postCreated.getUserId())
                .toString();
    }
}