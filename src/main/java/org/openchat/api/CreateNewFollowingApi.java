package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.usercases.CreateNewFollowingsService;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;
import org.openchat.usercases.FollowingRequest;
import org.openchat.usercases.ValidateFollowingExistService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateNewFollowingApi implements Route {

    private final CreateNewFollowingsService createNewFollowingsService;
    private final ValidateFollowingExistService validateFollowingExistService;

    public CreateNewFollowingApi(CreateNewFollowingsService createNewFollowingsService, ValidateFollowingExistService validateFollowingExistService) {
        this.createNewFollowingsService = createNewFollowingsService;
        this.validateFollowingExistService = validateFollowingExistService;
    }

    @Override
    public String handle(Request request, Response response) {
        FollowingRequest followingRequest = createFollowingRequest(request);

        try {
            validateFollowingExistService.execute(followingRequest);
            createNewFollowingsService.execute(followingRequest);
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
