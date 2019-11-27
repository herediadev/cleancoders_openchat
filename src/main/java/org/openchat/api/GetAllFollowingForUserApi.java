package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.FindUserByIdService;
import org.openchat.usercases.GetAllFollowingForUserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class GetAllFollowingForUserApi implements Route {

    private final GetAllFollowingForUserService getAllFollowingForUserService;
    private final FindUserByIdService findUserByIdService;

    public GetAllFollowingForUserApi(GetAllFollowingForUserService getAllFollowingForUserService, FindUserByIdService findUserByIdService) {
        this.getAllFollowingForUserService = getAllFollowingForUserService;
        this.findUserByIdService = findUserByIdService;
    }

    @Override
    public String handle(Request request, Response response) {
        String followerId = request.params("followerId");
        User username = findUserByIdService.execute(followerId);
        List<User> followingUserList = getAllFollowingForUserService.execute(username.getUsername());

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
