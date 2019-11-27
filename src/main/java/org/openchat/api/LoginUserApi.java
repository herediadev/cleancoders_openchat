package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.LoginUserRequest;
import org.openchat.usercases.LoginUserService;
import org.openchat.usercases.exceptions.InvalidCredentialException;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginUserApi implements Route {

    private final LoginUserService loginUserService;

    public LoginUserApi(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    public String handle(Request request, Response response) {
        LoginUserRequest loginUserRequest = this.createLoginUserRequestFromRequest(request);

        try {
            User user = this.loginUserService.execute(loginUserRequest);

            response.status(200);
            response.type("application/json");

            return new CreateNewUserResponse(user).invoke().toString();
        } catch (InvalidCredentialException e) {
            response.status(404);
            return "Invalid credentials.";
        }
    }

    private LoginUserRequest createLoginUserRequestFromRequest(Request request) {
        JsonObject requestBodyJson = Json.parse(request.body()).asObject();
        return new LoginUserRequest(
                requestBodyJson.getString("username", ""),
                requestBodyJson.getString("password", "")
        );
    }
}
