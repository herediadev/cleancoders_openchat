package org.openchat.api;

import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetUserWallApi implements Route {

    private final Function<String, List<Post>> getUserWallService;
    private final Function<List<Post>, String> createUserWallResponseService;

    public GetUserWallApi(Function<String, List<Post>> getUserWallService,
                          Function<List<Post>, String> createUserWallResponseService) {
        this.getUserWallService = getUserWallService;
        this.createUserWallResponseService = createUserWallResponseService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getUserWallService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(createUserWallResponseService)
                .apply(request);
    }

}
