package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

import java.util.UUID;
import java.util.function.Function;

public class CreateNewUserService implements Function<CreateNewUserRequest, User> {
    private final InMemoryUserRepository userRepository;

    public CreateNewUserService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User apply(CreateNewUserRequest createNewUserRequest) {
        User user = new User(UUID.randomUUID().toString(), createNewUserRequest.getUsername(), createNewUserRequest.getPassword(), createNewUserRequest.getAbout());

        this.userRepository.save(user);

        return user;
    }
}
