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
        String followerId = request.params("followerId");
        User username = findUserByIdService.apply(followerId);
        List<User> followingUserList = getAllFollowingForUserService.apply(username.getUsername());

        JsonArray collect = followingUserList.parallelStream()
                .map(this::createUserJson)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add));

        response.status(200);
        response.type("application/json");
        return collect.toString();
    }

    private JsonObject createUserJson(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout());
    }
}
