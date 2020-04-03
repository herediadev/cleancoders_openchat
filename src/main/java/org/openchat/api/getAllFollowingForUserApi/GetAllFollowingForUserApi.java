package org.openchat.api.getAllFollowingForUserApi;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GetAllFollowingForUserApi implements Route {

    private final Function<String, List<User>> getAllFollowingForUserService;
    private final Function<String, User> findUserByIdService;
    private final Function<List<User>, List<Map<String, String>>> getAllFollowingForUserResponsePresenter;

    public GetAllFollowingForUserApi(Function<String, List<User>> getAllFollowingForUserService,
                                     Function<String, User> findUserByIdService,
                                     Function<List<User>, List<Map<String, String>>> getAllFollowingForUserResponsePresenter) {
        this.getAllFollowingForUserService = getAllFollowingForUserService;
        this.findUserByIdService = findUserByIdService;
        this.getAllFollowingForUserResponsePresenter = getAllFollowingForUserResponsePresenter;
    }

    @Override
    public List<Map<String, String>> handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return Function.<String>identity()
                .compose(User::getUsername)
                .compose(findUserByIdService)
                .compose((Request requestParam) -> requestParam.params("followerId"))
                .andThen(getAllFollowingForUserService)
                .andThen(getAllFollowingForUserResponsePresenter)
                .apply(request);
    }
}
