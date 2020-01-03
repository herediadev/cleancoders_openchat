package org.openchat.api.getAllFollowingForUserApi;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetAllFollowingForUserApi implements Route {

    private final Function<String, List<User>> getAllFollowingForUserService;
    private final Function<String, User> findUserByIdService;
    private final Function<List<User>, JsonArray> getAllFollowingForUserResponsePresenter;

    public GetAllFollowingForUserApi(Function<String, List<User>> getAllFollowingForUserService,
                                     Function<String, User> findUserByIdService,
                                     Function<List<User>, JsonArray> getAllFollowingForUserResponsePresenter) {
        this.getAllFollowingForUserService = getAllFollowingForUserService;
        this.findUserByIdService = findUserByIdService;
        this.getAllFollowingForUserResponsePresenter = getAllFollowingForUserResponsePresenter;
    }

    @Override
    public JsonArray handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getAllFollowingForUserService
                .compose(User::getUsername)
                .compose(findUserByIdService)
                .compose((Request requestParam) -> requestParam.params("followerId"))
                .andThen(getAllFollowingForUserResponsePresenter)
                .apply(request);
    }
}
