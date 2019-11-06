package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.CreateNewUserRequest;
import org.openchat.usercases.CreateNewUserService;
import org.openchat.usercases.FindUserByUsernameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateNewUserApi implements Route {

    private final CreateNewUserService createNewUserService;
    private final FindUserByUsernameService findUserByUsernameService;

    public CreateNewUserApi(CreateNewUserService createNewUserService, FindUserByUsernameService findUserByUsernameService) {
        this.createNewUserService = createNewUserService;
        this.findUserByUsernameService = findUserByUsernameService;
    }

    public String handle(Request request, Response response) {
        CreateNewUserRequest newUserRequestFromRequest = this.createNewUserRequestFromRequest(request);

        User userAlreadyExist = findUserByUsernameService.execute(newUserRequestFromRequest.getUsername());
        if (userAlreadyExist != null)
            return setUserAlreadyExistResponse(response);

        User user = this.createNewUserService.execute(newUserRequestFromRequest);
        String newUserResponse = this.createNewUserResponse(user);
        return setResponse(newUserResponse, response);
    }

    private String setUserAlreadyExistResponse(Response response) {
        response.status(400);
        return "Username already in use.";
    }

    private String setResponse(String bodyResponse, Response response) {
        response.status(201);
        response.type("application/json");
        return bodyResponse;
    }

    private String createNewUserResponse(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout()
                ).toString();
    }

    private CreateNewUserRequest createNewUserRequestFromRequest(Request request) {
        JsonObject requestBodyJson = Json.parse(request.body()).asObject();
        return new CreateNewUserRequest(
                requestBodyJson.getString("username", ""),
                requestBodyJson.getString("password", ""),
                requestBodyJson.getString("about", "")
        );
    }
}
