package org.openchat.api;

import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetTimelineForUserApi implements Route {

    private final Function<String, List<Post>> getTimelineFromUserIdService;
    private final Function<List<Post>, String> createTimelineForUserResponseService;

    public GetTimelineForUserApi(Function<String, List<Post>> getTimelineFromUserIdService,
                                 Function<List<Post>, String> createTimelineForUserResponseService) {
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.createTimelineForUserResponseService = createTimelineForUserResponseService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getTimelineFromUserIdService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(createTimelineForUserResponseService)
                .apply(request);
    }
}
