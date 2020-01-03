package org.openchat.api.createNewUserApi;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
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
    private final Function<Request, CreateNewUserRequest> createNewUserRequestService;
    private final Function<User, JsonObject> createNewUserResponsePresenter;

    public CreateNewUserApi(Function<CreateNewUserRequest, User> createNewUserService,
                            Consumer<String> validaIfUserAlreadyExistService,
                            Function<Request, CreateNewUserRequest> createNewUserRequestService,
                            Function<User, JsonObject> createNewUserResponsePresenter) {
        this.createNewUserService = createNewUserService;
        this.validaIfUserAlreadyExistService = validaIfUserAlreadyExistService;
        this.createNewUserRequestService = createNewUserRequestService;
        this.createNewUserResponsePresenter = createNewUserResponsePresenter;
    }

    public String handle(Request request, Response response) {
        response.status(201);
        response.type("application/json");
        try {
            return this.createNewUserService
                    .compose(this::validaIfUserAlreadyExist)
                    .compose(createNewUserRequestService)
                    .andThen(createNewUserResponsePresenter)
                    .andThen(JsonValue::toString)
                    .apply(request);
        } catch (UserAlreadyExistException e) {
            return setUserAlreadyExistResponse(response);
        }
    }

    private CreateNewUserRequest validaIfUserAlreadyExist(CreateNewUserRequest newUserRequestFromRequest) {
        validaIfUserAlreadyExistService.accept(newUserRequestFromRequest.getUsername());
        return newUserRequestFromRequest;
    }

    private String setUserAlreadyExistResponse(Response response) {
        response.status(400);
        return "Username already in use.";
    }
}
