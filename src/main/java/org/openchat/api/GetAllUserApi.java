package org.openchat.api;

import com.eclipsesource.json.JsonArray;
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

        userList.stream().map(user -> new CreateNewUserResponse(user).invoke()).forEach(jsonUserList::add);

        return jsonUserList.toString();
    }

}
