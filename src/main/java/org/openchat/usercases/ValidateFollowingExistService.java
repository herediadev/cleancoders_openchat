package org.openchat.usercases;

public class ValidateFollowingExistService {
    private final InMemoryFollowingsRepository inMemoryFollowingsRepository;

    public ValidateFollowingExistService(InMemoryFollowingsRepository inMemoryFollowingsRepository) {
        this.inMemoryFollowingsRepository = inMemoryFollowingsRepository;
    }

    public void execute(FollowingRequest followingRequest) {
        inMemoryFollowingsRepository
                .checkIfExist(followingRequest)
                .ifPresent(this::throwFollowingAlreadyExistException);
    }

    private void throwFollowingAlreadyExistException(String followee) {
        throw new FollowingAlreadyExistException();
    }
}
