package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetUserWallApi implements Route {

    private final Function<String, List<Post>> getUserWallService;
    private final Function<List<Post>, JsonArray> createUserWallResponsePresenter;

    public GetUserWallApi(Function<String, List<Post>> getUserWallService,
                          Function<List<Post>, JsonArray> createUserWallResponsePresenter) {
        this.getUserWallService = getUserWallService;
        this.createUserWallResponsePresenter = createUserWallResponsePresenter;
    }

    @Override
    public JsonArray handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getUserWallService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(createUserWallResponsePresenter)
                .apply(request);
    }

}
