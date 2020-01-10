package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetAllUserApi implements Route {

    private final Supplier<List<User>> findAllUserService;
    private final Function<List<User>, JsonArray> createGetAllUserPresenter;

    public GetAllUserApi(Supplier<List<User>> findAllUserService,
                         Function<List<User>, JsonArray> createGetAllUserPresenter) {
        this.findAllUserService = findAllUserService;
        this.createGetAllUserPresenter = createGetAllUserPresenter;
    }

    @Override
    public JsonArray handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return Function.<List<User>>identity()
                .andThen(createGetAllUserPresenter)
                .apply(findAllUserService.get());
    }
}
