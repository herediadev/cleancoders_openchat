package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class GetTimelineFromUserApi implements Route {

    private final Function<String, List<Post>> getTimelineFromUserIdService;
    private final Function<LocalDateTime, String> formatDateService;

    public GetTimelineFromUserApi(Function<String, List<Post>> getTimelineFromUserIdService, Function<LocalDateTime, String> formatDateService) {
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.formatDateService = formatDateService;
    }

    @Override
    public String handle(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> userPosts = this.getTimelineFromUserIdService.apply(userId);

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
                .add("dateTime", formatDateService.apply(post.getDateTime()))
                .add("postId", post.getPostId())
                .add("text", post.getText())
                .add("userId", post.getUserId());
    }
}
