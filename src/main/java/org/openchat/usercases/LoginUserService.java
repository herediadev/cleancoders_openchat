package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.exceptions.InvalidCredentialException;

import java.util.Optional;
import java.util.function.Function;

public class LoginUserService implements Function<LoginUserRequest, User> {

    private final InMemoryUserRepository inMemoryUserRepository;

    public LoginUserService(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    public User apply(LoginUserRequest loginUserRequest) {
        Optional<User> userByUsername = inMemoryUserRepository.findUserByUsername(loginUserRequest.getUsername());

        userByUsername
                .filter(user -> user.getPassword().equals(loginUserRequest.getPassword()))
                .orElseThrow(InvalidCredentialException::new);

        return userByUsername.get();
    }
}
