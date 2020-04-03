package org.openchat.api.getUserWallApi;

import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetUserWallResponsePresenter implements Function<List<Post>, List<Map<String, String>>> {
    private final Function<LocalDateTime, String> formatDateService;

    public GetUserWallResponsePresenter(Function<LocalDateTime, String> formatDateService) {
        this.formatDateService = formatDateService;
    }

    @Override
    public List<Map<String, String>> apply(List<Post> userPosts) {
        return userPosts
                .stream()
                .map(this::createJsonPost)
                .collect(Collectors.toList());
    }

    private Map<String, String> createJsonPost(Post post) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("dateTime", formatDateService.apply(post.getDateTime()));
        map.put("postId", post.getPostId());
        map.put("text", post.getText());
        map.put("userId", post.getUserId());
        return map;
    }
}
