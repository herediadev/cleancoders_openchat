package org.openchat.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.CreateNewUserRequest;
import org.openchat.usercases.CreateNewUserService;
import org.openchat.usercases.FindAllUserService;

import java.util.List;

class FindAllUserServiceTest {

    private CreateNewUserService createNewUserService;
    private FindAllUserService findAllUserService;

    @BeforeEach
    void setUp() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        findAllUserService = new FindAllUserService(inMemoryUserRepository);
    }

    @Test
    void it_will_return_all_the_users() {
        //act
        User userAdded = createNewUserService.execute(new CreateNewUserRequest("username", "password", "about"));
        List<User> users = findAllUserService.execute();

        //assert
        Assertions.assertThat(users).containsExactly(userAdded);
    }
}