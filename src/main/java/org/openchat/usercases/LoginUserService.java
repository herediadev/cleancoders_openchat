package org.openchat.usercases;

import org.openchat.usercases.exceptions.InvalidCredentialException;
import org.openchat.entities.User;

public class LoginUserService {

    private final FindUserByUsernameService findUserByUsernameService;

    public LoginUserService(FindUserByUsernameService findUserByUsernameService) {
        this.findUserByUsernameService = findUserByUsernameService;
    }

    public User execute(LoginUserRequest loginUserRequest) {
        User userFound = findUserByUsernameService.execute(loginUserRequest.getUsername());

        if (userFound == null || !userFound.getPassword().equals(loginUserRequest.getPassword()))
            throw new InvalidCredentialException();

        return userFound;
    }
}
