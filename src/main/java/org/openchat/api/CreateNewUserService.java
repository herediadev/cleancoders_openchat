package org.openchat.api;

import java.util.UUID;

public class CreateNewUserService {
    private final InMemoryUserRepository userRepository;

    public CreateNewUserService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(CreateNewUserRequest createNewUserRequest) {
        User user = new User(UUID.randomUUID().toString(), createNewUserRequest.getUsername(), createNewUserRequest.getAbout());

        this.userRepository.execute(users -> users.add(user));

        return user;

    }
}
