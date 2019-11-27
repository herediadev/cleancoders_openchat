package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

public class FindUserByIdService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public FindUserByIdService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public User execute(String userId) {
        return inMemoryUserRepository
                .findUserById(userId)
                .orElse(new User("", "", "", ""));
    }
}
