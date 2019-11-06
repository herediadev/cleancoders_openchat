package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usercases.FindAllUserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class GetAllUserApi implements Route {

    private final FindAllUserService findAllUserService;

    public GetAllUserApi(FindAllUserService findAllUserService) {
        this.findAllUserService = findAllUserService;
    }

    @Override
    public String handle(Request request, Response response) {

        List<User> userList = findAllUserService.execute();

        response.status(200);
        response.type("application/json");

        JsonArray jsonUserList = new JsonArray();

        userList.stream().map(this::createNewUserResponse).forEach(jsonUserList::add);

        return jsonUserList.toString();
    }

    private String createNewUserResponse(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout()
                ).toString();
    }
}
