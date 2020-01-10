package org.openchat.api.createNewFollowingApi;

import org.openchat.usercases.FollowingRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Consumer;
import java.util.function.Function;

public class CreateNewFollowingApi implements Route {

    private final Consumer<FollowingRequest> createNewFollowingsService;
    private final Consumer<FollowingRequest> validateFollowingExistService;
    private final Function<Request, FollowingRequest> createNewFollowingRequestService;

    public CreateNewFollowingApi(Consumer<FollowingRequest> createNewFollowingsService,
                                 Consumer<FollowingRequest> validateFollowingExistService,
                                 Function<Request, FollowingRequest> createNewFollowingRequestService) {
        this.createNewFollowingsService = createNewFollowingsService;
        this.validateFollowingExistService = validateFollowingExistService;
        this.createNewFollowingRequestService = createNewFollowingRequestService;
    }

    @Override
    public String handle(Request request, Response response) {
        Function.<FollowingRequest>identity()
                .compose(createNewFollowingRequestService)
                .andThen(this::validateFollowingExist)
                .andThen(this::createFollowing)
                .apply(request);

        response.status(201);
        return "Following created.";
    }

    private FollowingRequest createFollowing(FollowingRequest followingRequest) {
        createNewFollowingsService.accept(followingRequest);
        return followingRequest;
    }

    private FollowingRequest validateFollowingExist(FollowingRequest followingRequest) {
        validateFollowingExistService.accept(followingRequest);
        return followingRequest;
    }

    public static void registerExceptionHandler(Exception exception, Request request, Response response) {
        response.status(400);
        response.body(exception.getMessage());
    }
}
