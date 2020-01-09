package org.openchat.api.createNewFollowingApi;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.usercases.FollowingRequest;
import spark.Request;

import java.util.function.Function;

public class CreateNewFollowingRequestService implements Function<Request, FollowingRequest> {

    public CreateNewFollowingRequestService() {
    }

    @Override
    public FollowingRequest apply(Request request) {
        JsonObject followingRequestJson = Json.parse(request.body()).asObject();
        return new FollowingRequest(
                followingRequestJson.getString("followeeId", ""),
                followingRequestJson.getString("followerId", ""));
    }
}
