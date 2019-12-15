package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.GetUserWallService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class GetUserWallApi implements Route {

    private final GetUserWallService getUserWallService;
    private final FormatDateService formatDateService;

    public GetUserWallApi(GetUserWallService getUserWallService, FormatDateService formatDateService) {
        this.getUserWallService = getUserWallService;
        this.formatDateService = formatDateService;
    }

    @Override
    public String handle(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> wall = getUserWallService.execute(userId);

        response.status(200);
        response.type("application/json");
        return createJsonResponse(wall);
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
