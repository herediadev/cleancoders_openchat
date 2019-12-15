package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.CreateNewPostService;
import org.openchat.usercases.CreatePostRequest;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateNewPostApi implements Route {

    private final CreateNewPostService createNewPostService;
    private final FormatDateService formatDateService;

    public CreateNewPostApi(CreateNewPostService createNewPostService, FormatDateService formatDateService) {
        this.createNewPostService = createNewPostService;
        this.formatDateService = formatDateService;
    }

    @Override
    public String handle(Request request, Response response) {
        CreatePostRequest createPostRequest = createPostRequest(request);

        try {
            Post postCreated = createNewPostService.apply(createPostRequest);
            response.status(201);
            response.type("application/json");

            return createNewPostResponse(postCreated);
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
                .add("dateTime", formatDateService.execute(postCreated.getDateTime()))
                .add("postId", postCreated.getPostId())
                .add("text", postCreated.getText())
                .add("userId", postCreated.getUserId())
                .toString();
    }

    private String getTextFromBody(Request request) {
        return Json.parse(request.body()).asObject().getString("text", "");
    }

}
