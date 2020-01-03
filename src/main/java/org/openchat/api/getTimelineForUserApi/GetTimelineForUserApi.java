package org.openchat.api.getTimelineForUserApi;

import com.eclipsesource.json.JsonArray;
import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.function.Function;

public class GetTimelineForUserApi implements Route {

    private final Function<String, List<Post>> getTimelineFromUserIdService;
    private final Function<List<Post>, JsonArray> getTimelineForUserResponsePresenter;

    public GetTimelineForUserApi(Function<String, List<Post>> getTimelineFromUserIdService,
                                 Function<List<Post>, JsonArray> getTimelineForUserResponsePresenter) {
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.getTimelineForUserResponsePresenter = getTimelineForUserResponsePresenter;
    }

    @Override
    public JsonArray handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getTimelineFromUserIdService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(getTimelineForUserResponsePresenter)
                .apply(request);
    }
}
