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
        validateFollowingExistService
                .andThen(createNewFollowingsService)
                .accept(createNewFollowingRequestService.apply(request));
        response.status(201);
        return "Following created.";
    }
}
