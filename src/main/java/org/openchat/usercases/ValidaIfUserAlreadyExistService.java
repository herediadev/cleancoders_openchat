package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

public class ValidaIfUserAlreadyExistService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public ValidaIfUserAlreadyExistService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public void execute(String username) {
        inMemoryUserRepository
                .findUserByUsername(username)
                .ifPresent(this::throwUserAlreadyExistException);
    }

    private void throwUserAlreadyExistException(User user) {
        throw new RuntimeException("user" + user.getUsername() + " already exist");
    }
}
