package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.GetTimelineFromUserIdService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class GetTimelineFromUserApi implements Route {

    private final GetTimelineFromUserIdService getTimelineFromUserIdService;
    private final FormatDateService formatDateService;

    public GetTimelineFromUserApi(GetTimelineFromUserIdService getTimelineFromUserIdService, FormatDateService formatDateService) {
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.formatDateService = formatDateService;
    }

    @Override
    public String handle(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> userPosts = this.getTimelineFromUserIdService.execute(userId);

        response.status(200);
        response.type("application/json");
        return createJsonResponse(userPosts);
    }

    private String createJsonResponse(List<Post> userPosts) {
        JsonArray jsonResponse = new JsonArray();
        userPosts
                .stream()
                .map(this::createJsonPost)
                .forEach(jsonResponse::add);

        return jsonResponse.toString();
    }

    private JsonObject createJsonPost(Post post) {
        return new JsonObject()
                .add("dateTime", formatDateService.execute(post.getDateTime()))
                .add("postId", post.getPostId())
                .add("text", post.getText())
                .add("userId", post.getUserId());
    }
}
