package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.CreateNewUserRequest;
import org.openchat.usercases.CreateNewUserService;
import org.openchat.usercases.exceptions.UserAlreadyExistException;
import org.openchat.usercases.ValidaIfUserAlreadyExistService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateNewUserApi implements Route {

    private final CreateNewUserService createNewUserService;
    private final ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService;

    public CreateNewUserApi(CreateNewUserService createNewUserService, ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService) {
        this.createNewUserService = createNewUserService;
        this.validaIfUserAlreadyExistService = validaIfUserAlreadyExistService;
    }

    public String handle(Request request, Response response) {
        CreateNewUserRequest newUserRequestFromRequest = this.createNewUserRequestFromRequest(request);

        try {
            validaIfUserAlreadyExistService.execute(newUserRequestFromRequest.getUsername());
            User user = this.createNewUserService.execute(newUserRequestFromRequest);
            String newUserResponse = new CreateNewUserResponse(user).invoke().toString();
            return setResponse(newUserResponse, response);

        } catch (UserAlreadyExistException e) {
            return setUserAlreadyExistResponse(response);
        }


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

    private CreateNewUserRequest createNewUserRequestFromRequest(Request request) {
        JsonObject requestBodyJson = Json.parse(request.body()).asObject();
        return new CreateNewUserRequest(
                requestBodyJson.getString("username", ""),
                requestBodyJson.getString("password", ""),
                requestBodyJson.getString("about", "")
        );
    }
}
