package org.openchat.api.createNewUserApi;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.usercases.CreateNewUserRequest;
import spark.Request;

import java.util.function.Function;

public class CreateNewUserRequestService implements Function<Request, CreateNewUserRequest> {

    public CreateNewUserRequestService() {
    }

    public CreateNewUserRequest apply(Request request) {
        JsonObject requestBodyJson = Json.parse(request.body()).asObject();
        return new CreateNewUserRequest(
                requestBodyJson.getString("username", ""),
                requestBodyJson.getString("password", ""),
                requestBodyJson.getString("about", "")
        );
    }
}
