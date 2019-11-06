package org.openchat.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.*;
import org.openchat.usercases.exceptions.InvalidCredentialException;

class LoginUserServiceTest {

    private CreateNewUserService createNewUserService;
    private LoginUserService loginUserService;

    @BeforeEach
    void setUp() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        loginUserService = new LoginUserService(inMemoryUserRepository);
    }

    @Test
    void given_the_login_user_request_it_will_authenticate_and_return_the_user_logged() {
        //arrange
        LoginUserRequest loginUserRequest = new LoginUserRequest("username", "password");
        CreateNewUserRequest createNewUserRequest = new CreateNewUserRequest("username", "password", "about");

        //act
        createNewUserService.execute(createNewUserRequest);
        User userLogged = loginUserService.execute(loginUserRequest);

        //assert
        Assertions.assertThat(userLogged.getUsername()).isEqualTo("username");
        Assertions.assertThat(userLogged.getPassword()).isEqualTo("password");
        Assertions.assertThat(userLogged.getAbout()).isEqualTo("about");
    }

    @Test
    void given_invalid_password_when_login_it_will_throw_an_exception() {
        //arrange
        LoginUserRequest loginUserRequestClass = new LoginUserRequest("username", "wrong_password");
        CreateNewUserRequest createNewUserRequest = new CreateNewUserRequest("username", "password", "about");

        //act and assert
        createNewUserService.execute(createNewUserRequest);
        org.junit.jupiter.api.Assertions.assertThrows(InvalidCredentialException.class, () -> loginUserService.execute(loginUserRequestClass));
    }

    @Test
    void given_invalid_username_when_login_it_will_throw_an_exception() {
        //arrange
        LoginUserRequest loginUserRequestClass = new LoginUserRequest("wrong_username", "password");
        CreateNewUserRequest createNewUserRequest = new CreateNewUserRequest("username", "password", "about");

        //act and assert
        createNewUserService.execute(createNewUserRequest);
        org.junit.jupiter.api.Assertions.assertThrows(InvalidCredentialException.class, () -> loginUserService.execute(loginUserRequestClass));
    }
}