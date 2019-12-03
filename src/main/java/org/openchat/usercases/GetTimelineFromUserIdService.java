package org.openchat.usercases;

import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;

import java.util.List;

public class GetTimelineFromUserIdService {

    private final InMemoryPostRepository inMemoryPostRepository;

    public GetTimelineFromUserIdService(InMemoryPostRepository inMemoryPostRepository) {
        this.inMemoryPostRepository = inMemoryPostRepository;
    }

    public List<Post> execute(String userId) {
        List<Post> postsByUserId = inMemoryPostRepository.findPostsByUserId(userId);

        postsByUserId.sort((post1, post2) -> post2.getDateTime().compareTo(post1.getDateTime()));

        return postsByUserId;
    }
}
