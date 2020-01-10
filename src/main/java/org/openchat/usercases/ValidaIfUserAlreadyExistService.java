package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.exceptions.UserAlreadyExistException;

import java.util.function.Consumer;

public class ValidaIfUserAlreadyExistService implements Consumer<String> {

    private final InMemoryUserRepository inMemoryUserRepository;

    public ValidaIfUserAlreadyExistService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public void accept(String username) {
        inMemoryUserRepository
                .findUserByUsername(username)
                .ifPresent(this::throwUserAlreadyExistException);
    }

    private void throwUserAlreadyExistException(User user) {
        throw new UserAlreadyExistException(user.getUsername());
    }
}
