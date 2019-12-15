package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.CreateNewUserRequest;
import org.openchat.usercases.exceptions.UserAlreadyExistException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Consumer;
import java.util.function.Function;

public class CreateNewUserApi implements Route {

    private final Function<CreateNewUserRequest, User> createNewUserService;
    private final Consumer<String> validaIfUserAlreadyExistService;

    public CreateNewUserApi(Function<CreateNewUserRequest, User> createNewUserService, Consumer<String> validaIfUserAlreadyExistService) {
        this.createNewUserService = createNewUserService;
        this.validaIfUserAlreadyExistService = validaIfUserAlreadyExistService;
    }

    public String handle(Request request, Response response) {
        try {
            return this.createNewUserService
                    .compose(this::validaIfUserAlreadyExist)
                    .compose(this::createNewUserRequestFromRequest)
                    .andThen(this::createNewUserResponse)
                    .andThen(newUserResponse -> setResponse(newUserResponse, response))
                    .apply(request);
        } catch (UserAlreadyExistException e) {
            return setUserAlreadyExistResponse(response);
        }
    }

    private String createNewUserResponse(User user) {
        return new CreateNewUserResponse(user).invoke().toString();
    }

    private CreateNewUserRequest validaIfUserAlreadyExist(CreateNewUserRequest newUserRequestFromRequest) {
        validaIfUserAlreadyExistService.accept(newUserRequestFromRequest.getUsername());
        return newUserRequestFromRequest;
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
