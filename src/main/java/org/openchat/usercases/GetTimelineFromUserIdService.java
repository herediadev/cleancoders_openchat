package org.openchat.usercases;

import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetTimelineFromUserIdService {

    private final InMemoryPostRepository inMemoryPostRepository;

    public GetTimelineFromUserIdService(InMemoryPostRepository inMemoryPostRepository) {
        this.inMemoryPostRepository = inMemoryPostRepository;
    }

    public List<Post> execute(String userId) {
        return inMemoryPostRepository.findPostsByUserId(userId)
                .stream()
                .sorted((post1, post2) -> post2.getDateTime().compareTo(post1.getDateTime()))
                .collect(Collectors.toList());
    }
}
