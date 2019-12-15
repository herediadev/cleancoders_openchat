package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Supplier;

public class GetAllUserApi implements Route {

    private final Supplier<List<User>> findAllUserService;

    public GetAllUserApi(Supplier<List<User>> findAllUserService) {
        this.findAllUserService = findAllUserService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return findAllUserService
                .get()
                .stream()
                .map(user -> new CreateNewUserResponse(user).invoke())
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add))
                .toString();
    }

}
