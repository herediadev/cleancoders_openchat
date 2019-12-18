package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class CreateTimelineForUserResponseService implements Function<List<Post>, String> {
    private Function<LocalDateTime, String> formatDateService;

    public CreateTimelineForUserResponseService(Function<LocalDateTime, String> formatDateService) {
        this.formatDateService = formatDateService;
    }

    public String apply(List<Post> userPosts) {
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
