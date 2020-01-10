package org.openchat.api.createNewUserApi;

import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.CreateNewUserRequest;
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

    @Override
    public JsonObject handle(Request request, Response response) {
        response.status(201);
        response.type("application/json");

        return Function.<CreateNewUserRequest>identity()
                .compose(this::validaIfUserAlreadyExist)
                .compose(createNewUserRequestService)
                .andThen(createNewUserService)
                .andThen(createNewUserResponsePresenter)
                .apply(request);
    }

    private CreateNewUserRequest validaIfUserAlreadyExist(CreateNewUserRequest newUserRequestFromRequest) {
        validaIfUserAlreadyExistService.accept(newUserRequestFromRequest.getUsername());
        return newUserRequestFromRequest;
    }
}
