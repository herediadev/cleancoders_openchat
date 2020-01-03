package org.openchat.api.getTimelineForUserApi;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class GetTimelineForUserResponsePresenter implements Function<List<Post>, JsonArray> {
    private Function<LocalDateTime, String> formatDateService;

    public GetTimelineForUserResponsePresenter(Function<LocalDateTime, String> formatDateService) {
        this.formatDateService = formatDateService;
    }

    @Override
    public JsonArray apply(List<Post> userPosts) {
        return userPosts
                .stream()
                .map(this::createJsonPost)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add));
    }

    private JsonObject createJsonPost(Post post) {
        return new JsonObject()
                .add("dateTime", formatDateService.apply(post.getDateTime()))
                .add("postId", post.getPostId())
                .add("text", post.getText())
                .add("userId", post.getUserId());
    }
}
