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

public class GetUserWallApi implements Route {

    private final Function<String, List<Post>> getUserWallService;
    private final Function<LocalDateTime, String> formatDateService;

    public GetUserWallApi(Function<String, List<Post>> getUserWallService, Function<LocalDateTime, String> formatDateService) {
        this.getUserWallService = getUserWallService;
        this.formatDateService = formatDateService;
    }

    @Override
    public String handle(Request request, Response response) {
        response.status(200);
        response.type("application/json");

        return getUserWallService
                .compose((Request requestParam) -> requestParam.params("userId"))
                .andThen(this::createJsonResponse)
                .apply(request);
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
