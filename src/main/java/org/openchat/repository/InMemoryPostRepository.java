package org.openchat.repository;

import org.openchat.entities.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPostRepository {

    private final List<Post> posts;

    public InMemoryPostRepository() {
        posts = new ArrayList<>();
    }

    public void save(Post post) {
        posts.add(post);
    }

    public Optional<Post> findPostById(String postId) {
        return posts
                .stream()
                .filter(post -> postId.equals(post.getPostId()))
                .findFirst();
    }
}
