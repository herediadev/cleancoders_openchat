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

    @Override
    public User apply(CreateNewUserRequest createNewUserRequest) {
        return Function.<User>identity()
                .compose(this::createUser)
                .andThen(this::saveUserToRepository)
                .apply(createNewUserRequest);
    }

    private User saveUserToRepository(User user) {
        userRepository.save(user);
        return user;
    }

    private User createUser(CreateNewUserRequest createNewUserRequest) {
        return new User(UUID.randomUUID().toString(), createNewUserRequest.getUsername(), createNewUserRequest.getPassword(), createNewUserRequest.getAbout());
    }
}
