package org.openchat.usercases;

public class CreateNewFollowingsService {

    private final InMemoryFollowingsRepository inMemoryFollowingsRepository;

    public CreateNewFollowingsService(InMemoryFollowingsRepository inMemoryFollowingsRepository) {
        this.inMemoryFollowingsRepository = inMemoryFollowingsRepository;
    }

    public void execute(FollowingRequest followingRequest) {
        inMemoryFollowingsRepository.addNewFollowing(followingRequest);
    }
}
