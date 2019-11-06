package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

import java.util.List;

public class FindAllUserService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public FindAllUserService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public List<User> execute() {
        return inMemoryUserRepository.getUserList();
    }
}
