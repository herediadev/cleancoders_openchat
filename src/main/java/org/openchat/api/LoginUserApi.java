package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.LoginUserRequest;
import org.openchat.usercases.exceptions.InvalidCredentialException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Function;

public class LoginUserApi implements Route {

    private final Function<LoginUserRequest, User> loginUserService;

    public LoginUserApi(Function<LoginUserRequest, User> loginUserService) {
        this.loginUserService = loginUserService;
    }

    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        try {
            return loginUserService
                    .compose(this::createLoginUserRequestFromRequest)
                    .andThen(this::createNewUserResponse)
                    .apply(request);
        } catch (InvalidCredentialException e) {
            response.status(404);
            return "Invalid credentials.";
        }
    }

    private String createNewUserResponse(User user) {
        return new CreateNewUserResponsePresenter().apply(user).toString();
    }

    private LoginUserRequest createLoginUserRequestFromRequest(Request request) {
        JsonObject requestBodyJson = Json.parse(request.body()).asObject();
        return new LoginUserRequest(
                requestBodyJson.getString("username", ""),
                requestBodyJson.getString("password", "")
        );
    }
}
