package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;
import org.openchat.usercases.GetAllPostFromUserIdService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class GetAllPostFromUserApi implements Route {

    private final GetAllPostFromUserIdService getAllPostFromUserIdService;
    private final FormatDateService formatDateService;

    public GetAllPostFromUserApi(GetAllPostFromUserIdService getAllPostFromUserIdService, FormatDateService formatDateService) {
        this.getAllPostFromUserIdService = getAllPostFromUserIdService;
        this.formatDateService = formatDateService;
    }

    public String handle(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> userPosts = this.getAllPostFromUserIdService.execute(userId);

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
                .add("postId",post.getPostId())
                .add("text",post.getText())
                .add("userId",post.getUserId());
    }
}
