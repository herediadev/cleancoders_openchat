package org.openchat.api.getUserWallApi;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetUserWallApi implements Route {

    private final Function<String, List<Post>> getUserWallService;
    private final Function<List<Post>, JsonArray> getUserWallResponsePresenter;

    public GetUserWallApi(Function<String, List<Post>> getUserWallService,
                          Function<List<Post>, JsonArray> getUserWallResponsePresenter) {
        this.getUserWallService = getUserWallService;
        this.getUserWallResponsePresenter = getUserWallResponsePresenter;
    }

    @Override
    public JsonArray handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getUserWallService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(getUserWallResponsePresenter)
                .apply(request);
    }

}
