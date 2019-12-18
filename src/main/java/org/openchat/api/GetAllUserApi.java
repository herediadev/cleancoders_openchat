package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetAllUserApi implements Route {

    private final Supplier<List<User>> findAllUserService;
    private final Function<User, JsonObject> createNewUserResponseService;

    public GetAllUserApi(Supplier<List<User>> findAllUserService,
                         Function<User, JsonObject> createNewUserResponseService) {
        this.findAllUserService = findAllUserService;
        this.createNewUserResponseService = createNewUserResponseService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return findAllUserService
                .get()
                .stream()
                .map(createNewUserResponseService)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add))
                .toString();
    }

}
