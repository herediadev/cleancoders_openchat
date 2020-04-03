package org.openchat.api.getUserWallApi;

import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GetUserWallApi implements Route {

    private final Function<String, List<Post>> getUserWallService;
    private final Function<List<Post>, List<Map<String, String>>> getUserWallResponsePresenter;

    public GetUserWallApi(Function<String, List<Post>> getUserWallService,
                          Function<List<Post>, List<Map<String, String>>> getUserWallResponsePresenter) {
        this.getUserWallService = getUserWallService;
        this.getUserWallResponsePresenter = getUserWallResponsePresenter;
    }

    @Override
    public List<Map<String, String>> handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return Function.<String>identity()
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(getUserWallService)
                .andThen(getUserWallResponsePresenter)
                .apply(request);
    }
}
