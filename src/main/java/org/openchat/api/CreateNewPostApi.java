package org.openchat.api;

import org.openchat.entities.Post;
import org.openchat.usercases.CreatePostRequest;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Function;

public class CreateNewPostApi implements Route {

    private final Function<CreatePostRequest, Post> createNewPostService;
    private final Function<Request, CreatePostRequest> createPostRequestService;
    private final Function<Post, String> CreateNewPostResponsePresenter;

    public CreateNewPostApi(Function<CreatePostRequest, Post> createNewPostService,
                            Function<Request, CreatePostRequest> createPostRequestService,
                            Function<Post, String> CreateNewPostResponsePresenter) {
        this.createNewPostService = createNewPostService;
        this.createPostRequestService = createPostRequestService;
        this.CreateNewPostResponsePresenter = CreateNewPostResponsePresenter;
    }

    @Override
    public String handle(Request request, Response response) {
        try {
            response.status(201);
            response.type("application/json");

            return createNewPostService
                    .compose(createPostRequestService)
                    .andThen(CreateNewPostResponsePresenter)
                    .apply(request);
        } catch (InappropriateLanguageException e) {
            response.status(400);
            return "Post contains inappropriate language.";
        }
    }

}
