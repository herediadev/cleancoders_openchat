package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

import java.util.function.Function;

public class FindUserByIdService implements Function<String, User> {

    private final InMemoryUserRepository inMemoryUserRepository;

    public FindUserByIdService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    @Override
    public User apply(String userId) {
        return inMemoryUserRepository
                .findUserById(userId)
                .orElse(new User("", "", "", ""));
    }
}
