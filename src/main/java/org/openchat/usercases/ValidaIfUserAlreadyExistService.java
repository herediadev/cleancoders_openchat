package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.exceptions.UserAlreadyExistException;

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
        throw new UserAlreadyExistException("user " + user.getUsername() + " already exist");
    }
}
