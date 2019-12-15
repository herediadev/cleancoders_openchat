package org.openchat.usercases;

import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;

import java.util.function.Consumer;

public class ValidateFollowingExistService implements Consumer<FollowingRequest> {
    private final InMemoryFollowingsRepository inMemoryFollowingsRepository;

    public ValidateFollowingExistService(InMemoryFollowingsRepository inMemoryFollowingsRepository) {
        this.inMemoryFollowingsRepository = inMemoryFollowingsRepository;
    }

    @Override
    public void accept(FollowingRequest followingRequest) {
        inMemoryFollowingsRepository
                .checkIfExist(followingRequest)
                .ifPresent(this::throwFollowingAlreadyExistException);
    }

    private void throwFollowingAlreadyExistException(String followee) {
        throw new FollowingAlreadyExistException();
    }
}
