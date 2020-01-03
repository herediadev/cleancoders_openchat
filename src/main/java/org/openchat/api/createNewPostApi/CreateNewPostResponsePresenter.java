package org.openchat.api.createNewPostApi;

import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.function.Function;

public class CreateNewPostResponsePresenter implements Function<Post, JsonObject> {
    private Function<LocalDateTime, String> formatDateService;

    public CreateNewPostResponsePresenter(Function<LocalDateTime, String> formatDateService) {
        this.formatDateService = formatDateService;
    }

    public JsonObject apply(Post postCreated) {
        return new JsonObject()
                .add("dateTime", formatDateService.apply(postCreated.getDateTime()))
                .add("postId", postCreated.getPostId())
                .add("text", postCreated.getText())
                .add("userId", postCreated.getUserId());
    }
}
