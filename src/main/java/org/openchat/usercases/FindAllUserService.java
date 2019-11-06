package org.openchat.usercases;

import org.openchat.repository.InMemoryUserRepository;
import org.openchat.entities.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FindAllUserService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public FindAllUserService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public List<User> execute() {

        AtomicReference<List<User>> allUsers = new AtomicReference<>();

        inMemoryUserRepository.execute(allUsers::set);

        return allUsers.get();
    }
}
