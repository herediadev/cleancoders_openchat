package org.openchat.api.createNewPostApi;

import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.CreatePostRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.function.Function;

public class CreateNewPostApi implements Route {

    private final Function<CreatePostRequest, Post> createNewPostService;
    private final Function<Request, CreatePostRequest> createNewPostRequestService;
    private final Function<Post, Map<String, String>> CreateNewPostResponsePresenter;

    public CreateNewPostApi(Function<CreatePostRequest, Post> createNewPostService,
                            Function<Request, CreatePostRequest> createNewPostRequestService,
                            Function<Post, Map<String, String>> CreateNewPostResponsePresenter) {
        this.createNewPostService = createNewPostService;
        this.createNewPostRequestService = createNewPostRequestService;
        this.CreateNewPostResponsePresenter = CreateNewPostResponsePresenter;
    }

    @Override
    public Map<String, String> handle(Request request, Response response) {
        response.status(201);
        response.type("application/json");

        return Function.<CreatePostRequest>identity()
                .compose(createNewPostRequestService)
                .andThen(createNewPostService)
                .andThen(CreateNewPostResponsePresenter)
                .apply(request);
    }
}
