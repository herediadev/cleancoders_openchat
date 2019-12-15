package org.openchat.usercases;

import org.openchat.repository.InMemoryFollowingsRepository;

import java.util.function.Consumer;

public class CreateNewFollowingsService implements Consumer<FollowingRequest> {

    private final InMemoryFollowingsRepository inMemoryFollowingsRepository;

    public CreateNewFollowingsService(InMemoryFollowingsRepository inMemoryFollowingsRepository) {
        this.inMemoryFollowingsRepository = inMemoryFollowingsRepository;
    }

    @Override
    public void accept(FollowingRequest followingRequest) {
        inMemoryFollowingsRepository.addNewFollowing(followingRequest);
    }
}
