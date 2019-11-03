package org.openchat.api;

import java.util.concurrent.atomic.AtomicReference;

public class FindUserByUsernameService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public FindUserByUsernameService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public User execute(String username) {
        AtomicReference<User> userFound = new AtomicReference<>();

        inMemoryUserRepository.execute(users -> users.stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .ifPresent(userFound::set));

        return userFound.get();

    }
}
