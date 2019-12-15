package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetAllFollowingForUserApi implements Route {

    private final Function<String, List<User>> getAllFollowingForUserService;
    private final Function<String, User> findUserByIdService;

    public GetAllFollowingForUserApi(Function<String, List<User>> getAllFollowingForUserService, Function<String, User> findUserByIdService) {
        this.getAllFollowingForUserService = getAllFollowingForUserService;
        this.findUserByIdService = findUserByIdService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        List<User> usersFollowings = getAllFollowingForUserService
                .compose(User::getUsername)
                .compose(findUserByIdService)
                .compose(this::getFollowerIdFromRequest)
                .apply(request);

        return usersFollowings
                .stream()
                .map(this::createUserJson)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add))
                .toString();
    }

    private String getFollowerIdFromRequest(Request requestParam) {
        return requestParam.params("followerId");
    }

    private JsonObject createUserJson(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout());
    }
}
