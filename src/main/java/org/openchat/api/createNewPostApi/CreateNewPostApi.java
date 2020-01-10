package org.openchat.api.createNewPostApi;

import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.CreatePostRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Function;

public class CreateNewPostApi implements Route {

    private final Function<CreatePostRequest, Post> createNewPostService;
    private final Function<Request, CreatePostRequest> createNewPostRequestService;
    private final Function<Post, JsonObject> CreateNewPostResponsePresenter;

    public CreateNewPostApi(Function<CreatePostRequest, Post> createNewPostService,
                            Function<Request, CreatePostRequest> createNewPostRequestService,
                            Function<Post, JsonObject> CreateNewPostResponsePresenter) {
        this.createNewPostService = createNewPostService;
        this.createNewPostRequestService = createNewPostRequestService;
        this.CreateNewPostResponsePresenter = CreateNewPostResponsePresenter;
    }

    @Override
    public JsonObject handle(Request request, Response response) {
        response.status(201);
        response.type("application/json");

        return Function.<CreatePostRequest>identity()
                .compose(createNewPostRequestService)
                .andThen(createNewPostService)
                .andThen(CreateNewPostResponsePresenter)
                .apply(request);
    }

    public static void registerExceptionHandler(Exception exception, Request request, Response response) {
        response.status(400);
        response.body(exception.getMessage());
    }
}
