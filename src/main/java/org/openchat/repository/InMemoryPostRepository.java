package org.openchat.repository;

import org.openchat.entities.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

    public List<Post> findPostsByUserId(String userId) {
        return posts
                .stream()
                .filter(post -> userId.equals(post.getUserId()))
                .collect(toList());
    }
}
