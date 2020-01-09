package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.LoginUserRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.Function;

public class LoginUserApi implements Route {

    private final Function<LoginUserRequest, User> loginUserService;
    private final Function<User, JsonObject> createNewUserResponsePresenter;

    public LoginUserApi(Function<LoginUserRequest, User> loginUserService, Function<User, JsonObject> createNewUserResponsePresenter) {
        this.loginUserService = loginUserService;
        this.createNewUserResponsePresenter = createNewUserResponsePresenter;
    }

    public JsonObject handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return loginUserService
                .compose(this::createLoginUserRequestFromRequest)
                .andThen(createNewUserResponsePresenter)
                .apply(request);
    }

    private LoginUserRequest createLoginUserRequestFromRequest(Request request) {
        JsonObject requestBodyJson = Json.parse(request.body()).asObject();
        return new LoginUserRequest(
                requestBodyJson.getString("username", ""),
                requestBodyJson.getString("password", "")
        );
    }
}
