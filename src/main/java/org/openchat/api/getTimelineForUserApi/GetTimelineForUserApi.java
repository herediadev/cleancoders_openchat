package org.openchat.api.getTimelineForUserApi;

import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetTimelineForUserApi implements Route {

    private final Function<String, List<Post>> getTimelineFromUserIdService;
    private final Function<List<Post>, String> getTimelineForUserResponsePresenter;

    public GetTimelineForUserApi(Function<String, List<Post>> getTimelineFromUserIdService,
                                 Function<List<Post>, String> getTimelineForUserResponsePresenter) {
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.getTimelineForUserResponsePresenter = getTimelineForUserResponsePresenter;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getTimelineFromUserIdService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(getTimelineForUserResponsePresenter)
                .apply(request);
    }
}
