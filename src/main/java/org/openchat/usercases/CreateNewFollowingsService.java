package org.openchat.usercases;

import org.openchat.repository.InMemoryFollowingsRepository;

public class CreateNewFollowingsService {

    private final InMemoryFollowingsRepository inMemoryFollowingsRepository;

    public CreateNewFollowingsService(InMemoryFollowingsRepository inMemoryFollowingsRepository) {
        this.inMemoryFollowingsRepository = inMemoryFollowingsRepository;
    }

    public void execute(FollowingRequest followingRequest) {
        inMemoryFollowingsRepository.addNewFollowing(followingRequest);
    }
}
