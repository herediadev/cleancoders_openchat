package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

import java.util.List;
import java.util.function.Supplier;

public class FindAllUserService implements Supplier<List<User>> {

    private final InMemoryUserRepository inMemoryUserRepository;

    public FindAllUserService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public List<User> get() {
        return inMemoryUserRepository.getUserList();
    }
}
