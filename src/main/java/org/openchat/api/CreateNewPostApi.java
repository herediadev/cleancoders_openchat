package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.CreatePostRequest;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
import java.util.function.Function;

public class CreateNewPostApi implements Route {

    private final Function<CreatePostRequest, Post> createNewPostService;
    private final Function<LocalDateTime, String> formatDateService;

    public CreateNewPostApi(Function<CreatePostRequest, Post> createNewPostService, Function<LocalDateTime, String> formatDateService) {
        this.createNewPostService = createNewPostService;
        this.formatDateService = formatDateService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(201);
        response.type("application/json");

        try {
            return createNewPostService
                    .compose(this::createPostRequest)
                    .andThen(this::createNewPostResponse)
                    .apply(request);
        } catch (InappropriateLanguageException e) {
            response.status(400);
            return "Post contains inappropriate language.";
        }
    }


    private CreatePostRequest createPostRequest(Request request) {
        String userId = request.params("userId");
        String text = getTextFromBody(request);
        return new CreatePostRequest(userId, text);
    }

    private String createNewPostResponse(Post postCreated) {
        return new JsonObject()
                .add("dateTime", formatDateService.apply(postCreated.getDateTime()))
                .add("postId", postCreated.getPostId())
                .add("text", postCreated.getText())
                .add("userId", postCreated.getUserId())
                .toString();
    }

    private String getTextFromBody(Request request) {
        return Json.parse(request.body()).asObject().getString("text", "");
    }

}
