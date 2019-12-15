package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.usercases.FollowingRequest;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Consumer;

public class CreateNewFollowingApi implements Route {

    private final Consumer<FollowingRequest> createNewFollowingsService;
    private final Consumer<FollowingRequest> validateFollowingExistService;

    public CreateNewFollowingApi(Consumer<FollowingRequest> createNewFollowingsService, Consumer<FollowingRequest> validateFollowingExistService) {
        this.createNewFollowingsService = createNewFollowingsService;
        this.validateFollowingExistService = validateFollowingExistService;
    }

    @Override
    public String handle(Request request, Response response) {
        try {
            validateFollowingExistService
                    .andThen(createNewFollowingsService)
                    .accept(createFollowingRequest(request));
            response.status(201);
            return "Following created.";
        } catch (FollowingAlreadyExistException e) {
            response.status(400);
            return "Following already exist.";
        }
    }

    private FollowingRequest createFollowingRequest(Request request) {
        JsonObject followingRequestJson = Json.parse(request.body()).asObject();
        return new FollowingRequest(
                followingRequestJson.getString("followeeId", ""),
                followingRequestJson.getString("followerId", ""));
    }
}
