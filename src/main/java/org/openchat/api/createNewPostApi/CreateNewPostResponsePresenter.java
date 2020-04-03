package org.openchat.api.createNewPostApi;

import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CreateNewPostResponsePresenter implements Function<Post, Map<String, String>> {
    private Function<LocalDateTime, String> formatDateService;

    public CreateNewPostResponsePresenter(Function<LocalDateTime, String> formatDateService) {
        this.formatDateService = formatDateService;
    }

    @Override
    public Map<String, String> apply(Post postCreated) {
        Map<String, String> map = new HashMap<>();
        map.put("dateTime", formatDateService.apply(postCreated.getDateTime()));
        map.put("postId", postCreated.getPostId());
        map.put("text", postCreated.getText());
        map.put("userId", postCreated.getUserId());
        return map;
    }
}
